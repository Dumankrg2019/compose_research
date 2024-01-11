package com.example.compose_research.domain.entity

sealed class AuthState{
    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}
