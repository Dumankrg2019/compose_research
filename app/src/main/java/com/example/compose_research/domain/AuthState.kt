package com.example.compose_research.domain

sealed class AuthState{
    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}
