package com.example.compose_research.domain.respository

import android.app.Application
import com.example.compose_research.data.mapper.NewsFeedMapper
import com.example.compose_research.data.network.ApiFactory
import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.entity.PostComment
import com.example.compose_research.domain.entity.StatisticItem
import com.example.compose_research.domain.entity.StatisticType
import com.example.compose_research.extensions.mergeWith
import com.example.compose_research.domain.entity.AuthState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

interface NewsFeedRepository{

    fun getAuthStateFlow(): StateFlow<AuthState>

   fun getRecommendations(): StateFlow<List<FeedPost>>

   fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>

   suspend fun loadNextData()

   suspend fun checkAuthState()

   suspend fun deletePost(feedPost: FeedPost)

   suspend fun changeLikeStatus(feedPost: FeedPost)
}