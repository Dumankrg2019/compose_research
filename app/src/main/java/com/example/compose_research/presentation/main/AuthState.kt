package com.example.compose_research.presentation.main

sealed class AuthState{
    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}
