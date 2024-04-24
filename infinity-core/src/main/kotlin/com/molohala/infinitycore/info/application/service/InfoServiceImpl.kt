package com.molohala.infinitycore.info.application.service

import com.molohala.infinitycommon.exception.custom.CustomException
import com.molohala.infinitycore.info.GithubInfoClient
import com.molohala.infinitycore.info.SolvedAcInfoClient
import com.molohala.infinitycore.info.application.dto.GithubUserInfo
import com.molohala.infinitycore.info.application.dto.SolvedAcSolves
import com.molohala.infinitycore.info.application.dto.res.MyInfoRes
import com.molohala.infinitycore.info.application.dto.res.SocialAccountRes
import com.molohala.infinitycore.info.application.dto.res.SolvedAcInfoRes
import com.molohala.infinitycore.info.exception.InfoExceptionCode
import com.molohala.infinitycore.member.application.MemberSessionHolder
import com.molohala.infinitycore.member.domain.consts.SocialType
import com.molohala.infinitycore.member.domain.entity.SocialAccount
import com.molohala.infinitycore.member.repository.SocialAccountJpaRepository
import com.molohala.infinitycore.member.repository.SocialAccountQueryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InfoServiceImpl(
    private val githubInfoClient: GithubInfoClient,
    private val solvedAcInfoClient: SolvedAcInfoClient,
    private val memberSessionHolder: MemberSessionHolder,
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

    override fun getMyInfo(): MyInfoRes {
        val member = memberSessionHolder.current()
        val socials = socialAccountJpaRepository.findSocialAccountsByMemberId(member.id!!) // id won't be null
            .map {
                SocialAccountRes(
                    it.socialId,
                    it.socialType
                )
            }
        return MyInfoRes(
            member.id,
            member.email,
            member.name,
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

        val socialExist = socialAccountQueryRepository.findSocialAccountByMemberIdAndType(member.id!!, SocialType.GITHUB)
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

        val socialExist = socialAccountQueryRepository.findSocialAccountByMemberIdAndType(member.id!!, SocialType.SOLVED_AC)
        if (socialExist != null) // btw, if social account already exists, it will be deleted.
            socialAccountJpaRepository.deleteById(socialExist.id!!)
        // TODO: Clear cache when ranking system created

        socialAccountJpaRepository.save(SocialAccount(name, SocialType.SOLVED_AC, member.id))
    }
}