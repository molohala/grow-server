package com.molohala.grow.infra.token

import com.molohala.grow.core.auth.IssueJwtToken
import com.molohala.grow.core.auth.application.dto.Token
import com.molohala.grow.core.auth.application.dto.res.ReissueTokenRes
import com.molohala.grow.core.auth.domain.consts.JwtType
import com.molohala.grow.core.member.domain.consts.MemberRole
import com.molohala.grow.infra.token.exception.ExpiredTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class IssueJwtTokenImpl(
    private val jwtProperties: JwtProperties,
    private val tokenExtractor: TokenExtractor
) : IssueJwtToken {

    override fun issueToken(email: String, role: MemberRole) =
        Token(
            issueToken(email, role, JwtType.ACCESS_TOKEN),
            issueToken(email, role, JwtType.REFRESH_TOKEN)
        )

    override fun reissueToken(refreshToken: String): ReissueTokenRes {
        try {
            val claims: Claims = tokenExtractor.extractClaims(refreshToken)
            val authorizationValue: String? = claims["Authorization"] as? String
            val role: MemberRole? = authorizationValue?.let { MemberRole.valueOf(it) }
            val accessToken: String = issueToken(claims.subject.toString(), role!!, JwtType.ACCESS_TOKEN)
            return ReissueTokenRes(accessToken)
        } catch (e: Exception) {
            throw ExpiredTokenException()
        }
    }

    private fun issueToken(email: String, role: MemberRole, type: JwtType): String =
        Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, type.jwtType)
            .setIssuer(jwtProperties.issuer)
            .setSubject(email)
            .claim("Authorization", role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + if (type == JwtType.ACCESS_TOKEN) jwtProperties.accessExpire else jwtProperties.refreshExpire))
            .signWith(tokenExtractor.signingKey(), SignatureAlgorithm.HS512)
            .compact()
}