package com.molohala.grow.infra.token


import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.infra.security.MemberDetailsService
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.apache.logging.log4j.util.Strings
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenExtractor(
    private val memberDetailsService: MemberDetailsService,
    private val jwtProperties: JwtProperties
) {
    fun signingKey() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret))

    fun getAuthentication(accessToken: String): Authentication {
        val claims = extractClaims(accessToken)
        val details = memberDetailsService.loadUserByUsername(claims.subject.toString())

        return UsernamePasswordAuthenticationToken(details, null, details.authorities)
    }

    fun extractClaims(token: String): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            throw CustomException(GlobalExceptionCode.TOKEN_EXPIRED)
        } catch (e: MalformedJwtException) {
            throw CustomException(GlobalExceptionCode.INVALID_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(GlobalExceptionCode.INVALID_TOKEN)
        }
    }

    fun extract(request: HttpServletRequest, type: String): String {
        val headers = request.getHeaders("Authorization")

        while (headers.hasMoreElements()) {
            val value = headers.nextElement()
            if (value.lowercase(Locale.getDefault()).startsWith(type.lowercase(Locale.getDefault()))) {
                return value.substring(type.length).trim { it <= ' ' }
            }
        }

        return Strings.EMPTY
    }
}