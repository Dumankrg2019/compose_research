package com.example.compose_research.data.repository

import android.app.Application
import com.example.compose_research.data.mapper.NewsFeedMapper
import com.example.compose_research.data.network.ApiFactory
import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.StatisticItem
import com.example.compose_research.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)


    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()
    suspend fun loadRecommendation(): List<FeedPost> {
        val response = apiService.loadRecommendations(getAccessToken())
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return posts
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
       val response = if(feedPost.isLiked) {
           apiService.deleteLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
       } else {
           apiService.addLike(
               token = getAccessToken(),
               ownerId = feedPost.communityId,
               postId = feedPost.id
           )
       }
       val newLikeCount = response.likes.count

        /*создается копия старой коллекции, которую можно менять
        //новая коллекция с элементами статистики - для этого брали старую коллекцию - делаем ее изменяемой
        посредством toMutableList() - т.е создается ее копия, чтобы в старой не делать никаких изменений
        */
        val newStatistics = feedPost.statistics.toMutableList().apply {
            //удаляем элемент, который хранит кол-во лайков
            removeIf { it.type == StatisticType.LIKES }
            //и вставляем новый
            add(StatisticItem(type = StatisticType.LIKES, newLikeCount))
        }

        /*далее создаем новый объект feedPost, который явл копией старого но сдвумя отличиями
        1) актальное кол-во лайков и 2) поменян статус isFavorite
         */
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)

        //получаем номер позиции по которому лежал старый объект, далее по этому  индексу положим новый объект
        val postIndex = _feedPosts.indexOf(feedPost)

        _feedPosts[postIndex] = newPost
    }
}