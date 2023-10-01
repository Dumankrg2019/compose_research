package com.example.compose_research.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
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
        drawerContent = {
            Text(text = "Item1")
        },
      bottomBar = {
          NavigationBar {
              NavigationBarItem(
                  selected = true,
                  onClick = {  },
                  icon = {
                      Icon(Icons.Filled.Favorite, contentDescription = null)
                  },
                  label = {Text(text="Favorite")}
              )
              NavigationBarItem(
                  selected = true,
                  onClick = {  },
                  icon = {
                      Icon(Icons.Outlined.Edit, contentDescription = null)
                  },
                  label = {Text(text="Edit")}
              )
              NavigationBarItem(
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