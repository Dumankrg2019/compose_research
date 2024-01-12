package com.example.compose_research.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.compose_research.navigation.AppNavGraph
import com.example.compose_research.navigation.rememberNavigationState
import com.example.compose_research.presentation.ViewModelFactory
import com.example.compose_research.presentation.comments.CommentsScreen
import com.example.compose_research.presentation.news.NewsFeedScreen



@Composable
fun VkNewsMS(
    viewModelFactory: ViewModelFactory
) {
    val navigationState = rememberNavigationState()

    Scaffold(modifier = Modifier.fillMaxWidth(), bottomBar = {
        BottomNavigation(
            modifier = Modifier.fillMaxWidth()
        ) {
            val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

            val items = listOf(
                NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile
            )
            items.forEach { item ->
                val selected = navBackStackEntry?.destination?.hierarchy?.any{
                    it.route == item.screen.route
                } ?: false
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        if(!selected) {
                            navigationState.navigateTo(item.screen.route)
                        }
                    },
                    icon = {
                        Icon(item.icon, contentDescription = null)
                    },
                    label = {
                        Text(text = stringResource(id = item.titleResId))
                    })
            }
        }
    }) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(
                    viewModelFactory = viewModelFactory,
                    paddingValues = paddingValues,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            commentsScreenContent = {feedPost->
                CommentsScreen(
                    viewModelFactory = viewModelFactory,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = feedPost
                )
            },
            favouriteScreenContent = { TextCounter("Favourite") },
            profileScreenContent = { TextCounter("Profile") }
        )
    }
}

@Composable
private fun TextCounter(name: String) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier.clickable {
            count++
        },
        text = "$name Count: $count",
        color = Color.Black
    )
}