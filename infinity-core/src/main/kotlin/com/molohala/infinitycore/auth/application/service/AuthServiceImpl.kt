package com.molohala.infinitycore.auth.application.service

import com.molohala.infinitycommon.exception.custom.InternalServerException
import com.molohala.infinitycore.auth.DodamMemberClient
import com.molohala.infinitycore.auth.IssueJwtToken
import com.molohala.infinitycore.auth.application.dto.DodamUserData
import com.molohala.infinitycore.auth.application.dto.Token
import com.molohala.infinitycore.auth.application.dto.req.ReissueTokenReq
import com.molohala.infinitycore.auth.application.dto.res.ReissueTokenRes
import com.molohala.infinitycore.member.domain.consts.MemberRole
import com.molohala.infinitycore.member.domain.consts.MemberState
import com.molohala.infinitycore.member.domain.entity.Member
import com.molohala.infinitycore.member.repository.MemberJpaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class AuthServiceImpl(
    private val dodamMemberClient: DodamMemberClient,
    private val issueJwtToken: IssueJwtToken,
    private val memberJpaRepository: MemberJpaRepository
): AuthService {

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun signIn(code: String): Token {
        val dodamUserData: DodamUserData? = dodamMemberClient.getMemberInfo(code)
        dodamUserData?.let { userData ->
            return withContext(Dispatchers.IO) {
                var member : Member? = memberJpaRepository.findByEmail(userData.email)
                if (member==null){
                    member =  save(userData)
                }else{
                    member.update(userData.email)
                }
                issueJwtToken.issueToken(member.email, member.role)
            }
        }?: throw InternalServerException()
    }

    private fun save(userData: DodamUserData): Member{
        return memberJpaRepository.save(Member(
                name = userData.name,
                email = userData.email,
                role = MemberRole.MEMBER,
                state = MemberState.ACTIVE
        ))
    }

    override fun reissue(reissueTokenReq: ReissueTokenReq): ReissueTokenRes =
        issueJwtToken.reissueToken(reissueTokenReq.refreshToken)

    override fun test(email: String): Token {
        println("엄")
        val member:Member? = memberJpaRepository.findByEmail(email)
        return issueJwtToken.issueToken(email, member!!.role)
    }

}