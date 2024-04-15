package com.molohala.infinityinfra.token


import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException
import com.molohala.infinitycommon.exception.custom.InternalServerException
import com.molohala.infinitycore.member.application.service.MemberService
import com.molohala.infinitycore.member.domain.entity.Member
import com.molohala.infinityinfra.security.MemberDetails
import io.jsonwebtoken.*
import jakarta.servlet.http.HttpServletRequest
import org.apache.logging.log4j.util.Strings
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenExtractor(
    private val memberService: MemberService,
    private val jwtProperties: JwtProperties
) {


    fun getAuthentication(accessToken: String): Authentication {
        val claims = extractClaims(accessToken)
        val member: Member? = memberService.getByEmail(claims.subject.toString())

        val details = MemberDetails(member)

        return UsernamePasswordAuthenticationToken(details, null, details.authorities)
    }

     fun extractClaims(token: String): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.secret)
                .build()
                .parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            throw CustomException(GlobalExceptionCode.TOKEN_EXPIRED)
        } catch (e: MalformedJwtException) {
            throw CustomException(GlobalExceptionCode.INVALID_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(GlobalExceptionCode.INVALID_TOKEN)
        } catch (e: Exception) {
            throw InternalServerException()
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