package com.example.compose_research.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_research.MainViewModel
import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.StatisticItem
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun VkNewsMS(
    viewModel: MainViewModel
) {

    Scaffold(modifier = Modifier.fillMaxWidth(), bottomBar = {
        NavigationBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            val selectedItemPosition = remember {
                mutableStateOf(0)
            }
            val items = listOf(
                NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile
            )
            items.forEachIndexed { index, item ->
                NavigationBarItem(selected = selectedItemPosition.value == index,
                    onClick = { selectedItemPosition.value = index },
                    icon = {
                        Icon(item.icon, contentDescription = null)
                    },
                    label = {
                        Text(text = stringResource(id = item.titleResId))
                    })
            }
        }
    }) {
        val feedPosts = viewModel.feedPosts.observeAsState(listOf())

        LazyColumn (
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(items = feedPosts.value, key = { it.id }) {feedPost->
                val dismissState = rememberDismissState()
                if(dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.removeVKBloc(feedPost)
                }
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        PostCard(
                            feedPost = feedPost,
                            onLikeClickListener = {statisticItem->
                                viewModel.updateCount(feedPost, statisticItem)
                            },
                            onViewsClickListener = {statisticItem->
                                viewModel.updateCount(feedPost, statisticItem)
                            },
                            //альтернативный способ записи работы с  лямбдой
                            onCommentClickListener = {statisticItem->
                                viewModel.updateCount(feedPost, statisticItem)
                            },
                            onShareClickListener = {statisticItem->
                                viewModel.updateCount(feedPost, statisticItem)
                            }
                        )
                    }
                )
            }
        }
    }
}