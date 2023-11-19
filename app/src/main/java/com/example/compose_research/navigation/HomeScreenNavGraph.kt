package com.example.compose_research.navigation

import android.accessibilityservice.AccessibilityService.ScreenshotResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.compose_research.domain.FeedPost
import com.example.compose_research.navigation.Screen.Companion.KEY_CONTENT_TEXT
import com.example.compose_research.navigation.Screen.Companion.KEY_FEED_POST_ID

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route,
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(KEY_FEED_POST_ID) {
                    type = NavType.IntType
                },
                navArgument(KEY_CONTENT_TEXT) {
                    type = NavType.StringType
                }
            )
        ) {
            //comments/{feed_post_id}
            val feedPostId = it.arguments?.getInt(KEY_FEED_POST_ID) ?: 0
            val feedPostText = it.arguments?.getString(KEY_CONTENT_TEXT) ?: "111"
            commentsScreenContent(
                FeedPost(
                    id = feedPostId,
                    contentText = feedPostText
                )
            )
        }
    }
}