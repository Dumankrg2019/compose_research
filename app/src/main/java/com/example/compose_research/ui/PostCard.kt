package com.example.compose_research.ui

import android.widget.AdapterView.OnItemClickListener
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_research.R
import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.StatisticItem
import com.example.compose_research.domain.StatisticType
import java.lang.IllegalStateException


@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onStatisticsItemClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.contentText)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.fillMaxWidth()
                    .height(250.dp),
                painter = painterResource(id = feedPost.contentImageResId),
                contentDescription = "post",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                statistics = feedPost.statistics,
                onItemClickListener = onStatisticsItemClickListener
            )
        }

    }
}


@Composable
private fun PostHeader(
    feedPost: FeedPost
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = feedPost.avatarResId),
            contentDescription = "group image"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = feedPost.communityName,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = feedPost.publicationDate,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "settings",
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}


@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onItemClickListener: (StatisticItem) -> Unit
) {
    Row {

        Row(
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = viewsItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(viewsItem)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                text = sharesItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(sharesItem)
                }
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = commentsItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(commentsItem)
                }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = R.drawable.ic_like,
                text = likesItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(likesItem)
                }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type:StatisticType)
: StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException("array is empty")
}
@Composable
private fun IconWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: () -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = MaterialTheme.colorScheme.onSecondary)
    }
}
