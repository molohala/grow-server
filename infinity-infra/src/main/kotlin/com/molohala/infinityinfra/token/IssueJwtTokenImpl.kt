package com.molohala.infinityinfra.token

import com.molohala.infinitycore.auth.IssueJwtToken
import com.molohala.infinitycore.auth.application.dto.Token
import com.molohala.infinitycore.auth.application.dto.res.ReissueTokenRes
import com.molohala.infinitycore.member.domain.consts.MemberRole
import com.molohala.infinityinfra.token.exception.ExpiredTokenException
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
): IssueJwtToken {

    override fun issueToken(email: String, role: MemberRole) =
        Token(
            issueAccessToken(email,role),
            issueRefreshToken(email, role)
        )

    override fun reissueToken(refreshToken: String): ReissueTokenRes {
        try {
            val claims: Claims = tokenExtractor.extractClaims(refreshToken)
            val authorizationValue: String? = claims["Authorization"] as? String
            val role: MemberRole? = authorizationValue?.let { MemberRole.valueOf(it) }
            val accessToken: String = issueAccessToken(claims.subject.toString(), role!!)
            return ReissueTokenRes(accessToken)
        } catch (e: Exception) {
            throw ExpiredTokenException()
        }
    }

    private fun issueAccessToken(email: String, role: MemberRole): String =
        Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, "ACCESS")
            .setIssuer(jwtProperties.issuer)
            .setSubject(email)
            .claim("Authorization", role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExpire))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
            .compact()

    private fun issueRefreshToken(email: String, role: MemberRole): String =
        Jwts.builder()
            .setHeaderParam(Header.JWT_TYPE, "REFRESH")
            .setIssuer(jwtProperties.issuer)
            .setSubject(email)
            .claim("Authorization", role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshExpire))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
            .compact()
}