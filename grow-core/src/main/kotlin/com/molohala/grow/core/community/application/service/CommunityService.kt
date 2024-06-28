package com.molohala.grow.core.community.application.service

import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.core.block.domain.repository.BlockRepository
import com.molohala.grow.core.comment.repository.QueryCommentRepository
import com.molohala.grow.core.common.PageRequest
import com.molohala.grow.core.community.application.dto.req.CommunityModifyReq
import com.molohala.grow.core.community.application.dto.req.CommunitySaveReq
import com.molohala.grow.core.community.application.dto.res.CommunityListRes
import com.molohala.grow.core.community.application.dto.res.CommunityRes
import com.molohala.grow.core.community.domain.consts.CommunityState
import com.molohala.grow.core.community.domain.entity.Community
import com.molohala.grow.core.community.domain.exception.CommunityNotFoundException
import com.molohala.grow.core.community.repository.CommunityJpaRepository
import com.molohala.grow.core.community.repository.CommunityQueryRepository
import com.molohala.grow.core.like.repository.LikeCachedRepository
import com.molohala.grow.core.like.repository.LikeQueryRepository
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.member.domain.entity.Member
import com.molohala.grow.core.member.domain.exception.AccessDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommunityService(
    private val communityJpaRepository: CommunityJpaRepository,
    private val queryCommunityRepository: CommunityQueryRepository,
    private val queryCommentRepository: QueryCommentRepository,
    private val likeCachedRepository: LikeCachedRepository,
    private val likeQueryRepository: LikeQueryRepository,
    private val blockRepository: BlockRepository,
    private val memberSessionHolder: MemberSessionHolder
) {

    @Transactional(rollbackFor = [Exception::class])
    fun save(communitySaveReq: CommunitySaveReq) {
        val entity = communityJpaRepository.save(
            Community(
                communitySaveReq.content,
                CommunityState.ACTIVE,
                memberSessionHolder.current().id!!
            )
        )
        likeCachedRepository.cache(entity.id!!, 0)
    }

    fun getList(page: PageRequest): List<CommunityListRes> {
        val member = memberSessionHolder.current()
        if (page.page < 1) throw CustomException(GlobalExceptionCode.INVALID_PARAMETER)
        return queryCommunityRepository.findWithPagination(pageRequest = page, userId = member.id!!).map {
            val likedCount = likeQueryRepository.getCntByCommunityId(it.communityId)
            likeCachedRepository.cache(it.communityId, likedCount)
            CommunityListRes(
                CommunityRes(
                    it.communityId,
                    it.content,
                    it.createdAt,
                    likedCount,
                    likeQueryRepository.existsByCommunityIdAndMemberId(it.communityId, member.id),
                    it.writerName,
                    it.writerId
                ), queryCommentRepository.findRecentComment(it.communityId, userId = member.id)
            )
        }
    }

    fun getById(id: Long): CommunityRes? {
        val member: Member = memberSessionHolder.current()
        val likeCnt: Long = likeQueryRepository.getCntByCommunityId(id)
        val isLike: Boolean = likeQueryRepository
            .existsByCommunityIdAndMemberId(id, member.id!!)
        return queryCommunityRepository.findById(id, likeCnt, isLike)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun modify(communityModifyReq: CommunityModifyReq) {
        val community = communityJpaRepository
            .findByIdOrNull(communityModifyReq.id) ?: throw CommunityNotFoundException()
        val curMember = memberSessionHolder.current()
        if (curMember.id != community.memberId) {
            throw AccessDeniedException()
        }
        community.modify(communityModifyReq.content)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun delete(id: Long) {
        val community = communityJpaRepository
            .findByIdOrNull(id) ?: throw CommunityNotFoundException()
        val curMember = memberSessionHolder.current()
        if (curMember.id != community.memberId) {
            throw AccessDeniedException()
        }
        likeCachedRepository.clear(id)
        community.delete()
    }

    fun getBestCommunity(count: Int): List<CommunityListRes> {
        val member: Member = memberSessionHolder.current()
        val blocks = blockRepository.findByUserId(userId = member.id!!)
        return likeCachedRepository.getAll(-1)
            .mapNotNull {
                val didLiked =
                    likeQueryRepository.existsByCommunityIdAndMemberId(it.communityId, member.id)
                queryCommunityRepository.findById(it.communityId, it.likeCount, didLiked)
                    ?: likeCachedRepository.clear(it.communityId).let { null }
            }
            .filter { community ->
                blocks.firstOrNull { it.blockedUserId == community.writerId } == null
            }
            .map {
                CommunityListRes(
                    it,
                    queryCommentRepository.findRecentComment(communityId = it.communityId, userId = member.id!!)
                )
            }
            .let {
                if (it.size >= count) {
                    it.slice(0..<count)
                } else {
                    it
                }
            }
    }
}
