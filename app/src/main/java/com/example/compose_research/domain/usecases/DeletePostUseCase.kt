package com.example.compose_research.domain.usecases

import com.example.compose_research.domain.entity.AuthState
import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.entity.PostComment
import com.example.compose_research.domain.respository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class DeletePostUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(feedPost: FeedPost) {
         repository.deletePost(feedPost)
    }
}



