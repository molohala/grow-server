package com.molohala.infinitycore.like.application.service

import com.molohala.infinitycore.like.domain.entity.Like
import com.molohala.infinitycore.like.repository.LikeJpaRepository
import com.molohala.infinitycore.like.repository.LikeQueryRepository
import com.molohala.infinitycore.member.application.MemberSessionHolder
import com.molohala.infinitycore.member.domain.entity.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeJpaRepository: LikeJpaRepository,
    private val likeQueryRepository: LikeQueryRepository,
    private val memberSessionHolder: MemberSessionHolder
) {

    @Transactional(rollbackFor = [Exception::class])
    fun patch(communityId: Long) {
        val member: Member = memberSessionHolder.current()
        val isExist: Boolean = likeQueryRepository
            .existsByCommunityIdAndMemberId(communityId, member.id!!)
        if (isExist) {
            likeJpaRepository.delete(
                likeQueryRepository.findByCommunityIdAndMemberId(communityId, member.id)
            )
        } else {
            likeJpaRepository.save(
                Like(communityId, member.id)
            )
        }
    }

    fun getCnt(communityId: Long): Long {
        return likeQueryRepository.getCntByCommunityId(communityId)
    }
}