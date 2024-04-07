package com.molohala.infinityinfra.api.dodam

import com.molohala.infinitycore.auth.DodamMemberClient
import com.molohala.infinitycore.auth.application.dto.DodamUserData
import com.molohala.infinityinfra.webclient.WebClientSupport
import org.springframework.stereotype.Component

@Component
class DodamClient(
    private val dodamProperties: DodamProperties,
    private val webClientSupport: WebClientSupport
): DodamMemberClient {

    fun issueDodamToken(code: String): String? {
        return webClientSupport.post(
            dodamProperties.tokenUrl,
            IssueDodamTokenReq(
                code,
                dodamProperties.clientId,
                dodamProperties.clientSecret
            ),
            DodamToken::class.java
        ).block()?.access_token
    }

    override suspend fun getMemberInfo(
        code: String
    ): DodamUserData? {
        val token: String? = issueDodamToken(code)
        val dodamUser: DodamUserData? = webClientSupport.get(
            dodamProperties.infoUrl,
            DodamUserResponse::class.java,
            *arrayOf("Authorization", "Bearer $token")
        ).block()?.data
        return dodamUser
    }
}


