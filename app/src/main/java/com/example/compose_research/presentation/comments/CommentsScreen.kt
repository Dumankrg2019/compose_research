package com.example.compose_research.presentation.comments

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.compose_research.R
import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.entity.PostComment
import com.example.compose_research.presentation.NewsFeedApplication
import com.example.compose_research.presentation.ViewModelFactory
import com.example.compose_research.presentation.getApplicationComponent


@Composable
fun CommentsScreen(
    onBackPressed: () -> Unit,
    feedPost: FeedPost,
) {
    val component = getApplicationComponent()
        .getCommentsScreenComponentFactory()
        .create(feedPost)
    val viewModel: CommentsViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val screenState = viewModel.screenState.collectAsState(CommentsScreenState.Initial)
    CommentScreenContent(
        screenState = screenState,
        onBackPressed = onBackPressed
    )
}


@Composable
private fun CommentScreenContent(
    screenState: State<CommentsScreenState>,
    onBackPressed: () -> Unit,
) {
    val currentState = screenState.value
    Log.e("comments", "comments show")
    if (currentState is CommentsScreenState.Comments) {
        Log.e("comments", "comments show - $currentState")
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.comments_title))
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Log.e("comments", "before paddingValues")
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = currentState.comments,
                    key = { it.id }
                ) { comment ->
                    CommentItem(comment = comment)
                    Log.e("comments", "LazyColumn - items")
                }
            }
        }
    }
}
@Composable
private fun CommentItem(
    comment: PostComment
) {
    Log.e("comments", "Into CommentItem")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = comment.authorAvatarUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = comment.authorName,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.commentText,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.publicationDate,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 12.sp
            )
        }
    }
}