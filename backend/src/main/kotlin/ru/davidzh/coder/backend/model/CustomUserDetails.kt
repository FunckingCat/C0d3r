package ru.davidzh.coder.backend.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    val id: String,
    val _username: String,
    val _authorities: Collection<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities() = _authorities

    override fun getPassword() = null
    override fun getUsername(): String = _username
}
