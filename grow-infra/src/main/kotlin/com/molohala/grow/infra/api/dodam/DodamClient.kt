package com.molohala.grow.infra.api.dodam

import com.molohala.grow.core.auth.DodamMemberClient
import com.molohala.grow.core.auth.application.dto.DodamUserData
import com.molohala.grow.infra.webclient.WebClientSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class DodamClient(
    private val dodamProperties: DodamProperties,
    private val webClientSupport: WebClientSupport
) : DodamMemberClient {

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
        val dodamUser: DodamUserData? = withContext(Dispatchers.IO) {
            webClientSupport.get(
                dodamProperties.infoUrl,
                DodamUserResponse::class.java,
                *arrayOf("Authorization", "Bearer $token")
            ).block()
        }?.data
        return dodamUser
    }
}


