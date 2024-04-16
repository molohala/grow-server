package com.molohala.infinityinfra.api.solvedac

import com.molohala.infinitycore.info.SolvedAcInfoClient
import com.molohala.infinitycore.info.application.dto.SolvedAcSolves
import com.molohala.infinitycore.info.application.dto.SolvedAcUserInfo
import com.molohala.infinityinfra.webclient.WebClientSupport
import com.molohala.infinityinfra.webclient.exception.WebClientException
import org.springframework.stereotype.Component

@Component
class SolvedAcClient(
    val webClientSupport: WebClientSupport
) : SolvedAcInfoClient {
    override fun getUserInfo(name: String): SolvedAcUserInfo? {
        return try {
            val userInfo = webClientSupport.get(
                "https://solved.ac/api/v3/user/show?handle=$name",
                SolvedAcUserInfoResponse::class.java
            ).block()!!
            SolvedAcUserInfo(
                userInfo.handle,
                userInfo.profileImageUrl,
                userInfo.bio,
                userInfo.tier,
                userInfo.rating,
                userInfo.maxStreak,
                userInfo.rank,
                userInfo.solvedCount,
            )
        } catch (e: WebClientException) {
            null // TODO: cleaner error handling
        }
    }

    override fun getUserGrass(name: String): List<SolvedAcSolves> {
        return try {
            webClientSupport.get(
                "https://solved.ac/api/v3/user/grass?handle=$name&topic=default",
                SolvedAcGrassResponse::class.java
            ).block()!!.grass
        } catch (e: WebClientException) {
            emptyList() // TODO: cleaner error handling
        }
    }
}