package com.example.compose_research.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_research.presentation.news.NewsFeedViewModel



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyColumnSample(viewModel: NewsFeedViewModel) {
    val models = viewModel.models.observeAsState(listOf())
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LazyColumn(
        state = lazyListState
    ) {
        items(models.value, key = {it.id}) { model ->
            val dismissState = rememberDismissState()
            if(dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.delete(model)
            }
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                             Box(modifier = Modifier
                                 .padding(16.dp)
                                 .background(Color.Red.copy(alpha = 0.5f))
                                 .fillMaxSize(),
                                 contentAlignment = Alignment.Center
                             ) {
                                 Text(
                                     modifier = Modifier.padding(16.dp),
                                     text = "Delete item",
                                     color = Color.White,
                                     fontSize = 24.sp
                                 )
                             }
                },
                dismissContent = {
                    InstagramProfileCard(model = model, onFollowedButtonClickListener = {
                        viewModel.changeFollowingStatus(it)
                    })
                }
            )
        }
    }
}