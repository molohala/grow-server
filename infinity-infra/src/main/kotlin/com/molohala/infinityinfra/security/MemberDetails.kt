package com.molohala.infinityinfra.security

import com.molohala.infinitycore.member.domain.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MemberDetails(internal val member: Member?) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(GrantedAuthority { member?.role.toString() })
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return member?.email
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return false
    }
}
