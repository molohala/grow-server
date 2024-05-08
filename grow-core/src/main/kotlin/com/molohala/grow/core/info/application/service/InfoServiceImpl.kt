package com.molohala.grow.core.info.application.service

import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.core.info.GithubInfoClient
import com.molohala.grow.core.info.SolvedAcInfoClient
import com.molohala.grow.core.info.application.dto.GithubUserInfo
import com.molohala.grow.core.info.application.dto.SolvedAcSolves
import com.molohala.grow.core.info.application.dto.res.InfoRes
import com.molohala.grow.core.info.application.dto.res.SocialAccountRes
import com.molohala.grow.core.info.application.dto.res.SolvedAcInfoRes
import com.molohala.grow.core.info.exception.InfoExceptionCode
import com.molohala.grow.core.member.application.MemberSessionHolder
import com.molohala.grow.core.member.domain.consts.MemberJob
import com.molohala.grow.core.member.domain.consts.SocialType
import com.molohala.grow.core.member.domain.entity.SocialAccount
import com.molohala.grow.core.member.repository.MemberJpaRepository
import com.molohala.grow.core.member.repository.SocialAccountJpaRepository
import com.molohala.grow.core.member.repository.SocialAccountQueryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InfoServiceImpl(
    private val githubInfoClient: GithubInfoClient,
    private val solvedAcInfoClient: SolvedAcInfoClient,
    private val memberSessionHolder: MemberSessionHolder,
    private val memberJpaRepository: MemberJpaRepository,
    private val socialAccountJpaRepository: SocialAccountJpaRepository,
    private val socialAccountQueryRepository: SocialAccountQueryRepository,
) : InfoService {
    override fun getGithubInfo(name: String): GithubUserInfo {
        return githubInfoClient.getInfo(name) ?: throw CustomException(InfoExceptionCode.USER_NOT_FOUND)
    }

    override fun getSolvedAcInfo(name: String): SolvedAcInfoRes {
        val user = solvedAcInfoClient.getUserInfo(name)
            ?: throw CustomException(InfoExceptionCode.USER_NOT_FOUND)
        val grass = solvedAcInfoClient.getUserGrass(name)
        val today = LocalDate.now()
        val lastWeekSolves = today.minusDays(7).datesUntil(today).toList().map { date ->
            grass.find { it.date == date } ?: SolvedAcSolves(date)
        }
        return SolvedAcInfoRes(
            user.name,
            user.avatarUrl,
            user.bio,
            user.tier,
            user.rating,
            user.maxStreak,
            user.totalRank,
            user.totalSolves,
            lastWeekSolves,
            grass.find { it.date == today } ?: SolvedAcSolves(today)
        )
    }

    override fun getMyInfo(): InfoRes {
        val member = memberSessionHolder.current()
        val socials = socialAccountJpaRepository.findSocialAccountsByMemberId(member.id!!) // id won't be null
            .map {
                SocialAccountRes(
                    it.socialId,
                    it.socialType
                )
            }
        return InfoRes(
            member.id,
            member.email,
            member.name,
            member.bio,
            member.job.display,
            member.createdAt,
            socials
        )
    }

    override fun getUserInfo(userId: Long): InfoRes {
        val member = memberJpaRepository.findById(userId)
            .orElseThrow { CustomException(InfoExceptionCode.USER_NOT_FOUND) }
        val socials = socialAccountJpaRepository.findSocialAccountsByMemberId(member.id!!) // id won't be null
            .map {
                SocialAccountRes(
                    it.socialId,
                    it.socialType
                )
            }
        return InfoRes(
            member.id,
            member.email,
            member.name,
            member.bio,
            member.job.display,
            member.createdAt,
            socials
        )
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun submitGithubId(name: String) {
        val member = memberSessionHolder.current()

        // social exist check
        githubInfoClient.getInfo(name)
            ?: throw CustomException(InfoExceptionCode.USER_NOT_FOUND)
        // if no error thrown, then create

        val socialExist =
            socialAccountQueryRepository.findSocialAccountByMemberIdAndType(member.id!!, SocialType.GITHUB)
        if (socialExist != null) // btw, if social account already exists, it will be deleted.
            socialAccountJpaRepository.deleteById(socialExist.id!!)
        // TODO: Clear cache when ranking system created

        socialAccountJpaRepository.save(SocialAccount(name, SocialType.GITHUB, member.id))
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun submitSolvedAcInfo(name: String) {
        val member = memberSessionHolder.current()

        // social exist check
        solvedAcInfoClient.getUserInfo(name)
            ?: throw CustomException(InfoExceptionCode.USER_NOT_FOUND)
        // if no error thrown, then create

        val socialExist =
            socialAccountQueryRepository.findSocialAccountByMemberIdAndType(member.id!!, SocialType.SOLVED_AC)
        if (socialExist != null) // btw, if social account already exists, it will be deleted.
            socialAccountJpaRepository.deleteById(socialExist.id!!)
        // TODO: Clear cache when ranking system created

        socialAccountJpaRepository.save(SocialAccount(name, SocialType.SOLVED_AC, member.id))
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun editInfo(bio: String?, job: String?) {
        memberJpaRepository.save(
            memberSessionHolder.current().apply { updateInfo(bio ?: this.bio, MemberJob.parse(job) ?: this.job) }
        )
    }
}