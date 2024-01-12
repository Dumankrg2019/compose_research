package com.example.compose_research.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ScaffoldSample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Text(text = "Top AppBar Title")
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        },
      bottomBar = {
          BottomNavigation {
              BottomNavigationItem(
                  selected = true,
                  onClick = {  },
                  icon = {
                      Icon(Icons.Filled.Favorite, contentDescription = null)
                  },
                  label = {Text(text="Favorite")}
              )
              BottomNavigationItem(
                  selected = true,
                  onClick = {  },
                  icon = {
                      Icon(Icons.Outlined.Edit, contentDescription = null)
                  },
                  label = {Text(text="Edit")}
              )
              BottomNavigationItem(
                  selected = true,
                  onClick = {  },
                  icon = {
                      Icon(Icons.Outlined.Delete, contentDescription = null)
                  },
                  label = {Text(text="Delete")}
              )
          }
      },
    ) {
        Text(
            text = "This is scaffold content",
            modifier = Modifier.padding(it)
        )
    }
}