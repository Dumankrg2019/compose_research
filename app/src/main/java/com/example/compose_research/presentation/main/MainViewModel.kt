package com.example.compose_research.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compose_research.presentation.main.AuthState
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState> (AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token != null && token.isValid
        Log.e("MainViewModel", "token is ${token?.accessToken}")
        _authState.value = if(loggedIn) AuthState.Authorized else AuthState.NotAuthorized
    }
    fun performAuthResult(result: VKAuthenticationResult) {
        if(result is VKAuthenticationResult.Success) {
            _authState.value = AuthState.Authorized
            Log.e("MainViewModel", "success auth: ${result.token.accessToken}" +
                    "\n${result.token}")
        } else {
            (result as VKAuthenticationResult.Failed)
            _authState.value = AuthState.NotAuthorized
            Log.e("auth error", result.exception.localizedMessage)
        }
    }

}