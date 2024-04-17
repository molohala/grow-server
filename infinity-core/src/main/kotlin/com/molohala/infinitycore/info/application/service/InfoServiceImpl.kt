package com.molohala.infinitycore.info.application.service

import com.molohala.infinitycommon.exception.custom.CustomException
import com.molohala.infinitycore.info.GithubInfoClient
import com.molohala.infinitycore.info.SolvedAcInfoClient
import com.molohala.infinitycore.info.application.dto.GithubUserInfo
import com.molohala.infinitycore.info.application.dto.SolvedAcSolves
import com.molohala.infinitycore.info.application.dto.res.MyInfoRes
import com.molohala.infinitycore.info.application.dto.res.SolvedAcInfoRes
import com.molohala.infinitycore.info.exception.InfoExceptionCode
import com.molohala.infinitycore.member.application.MemberSessionHolder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InfoServiceImpl(
    private val githubInfoClient: GithubInfoClient,
    private val solvedAcInfoClient: SolvedAcInfoClient,
    private val memberSessionHolder: MemberSessionHolder
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
        val cur = memberSessionHolder.current()
        return MyInfoRes(
            cur.id!!, // id won't be null
            cur.email,
            cur.name,
            cur.createdAt,
        )
    }
}