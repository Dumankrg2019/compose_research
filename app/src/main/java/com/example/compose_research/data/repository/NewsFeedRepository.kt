package com.example.compose_research.data.repository

import android.app.Application
import com.example.compose_research.data.mapper.NewsFeedMapper
import com.example.compose_research.data.network.ApiFactory
import com.example.compose_research.domain.FeedPost
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)


    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    suspend fun loadRecommendation(): List<FeedPost> {
        val response = apiService.loadRecommendations(getAccessToken())
        return mapper.mapResponseToPosts(response)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }
}