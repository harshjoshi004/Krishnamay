package com.example.krishnamaya1.stories

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.krishnamaya1.BoldSubheading
import com.example.krishnamaya1.Subheading
import com.example.krishnamaya1.ui.theme.ElevatedMustard2
import kotlinx.coroutines.delay

@Composable
fun StoryPresenter(storyId: String?,navController: NavController) {
    val storiesViewModel: StoriesViewModel = viewModel()
    var story by remember { mutableStateOf<Story?>(null) }

    var progress by remember { mutableStateOf(1f) } // Starts at full width
    val durationMillis = 10_000 // 10 seconds

    LaunchedEffect(Unit) {
        storiesViewModel.getStoryById(storyId!!,
            onSuccess =  {
                story = it
            },
            onFailure = {}
        )
    }

    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        while (progress > 0) {
            val elapsed = System.currentTimeMillis() - startTime
            progress = 1f - (elapsed / durationMillis.toFloat())
            delay(50) // Refresh every 50ms for smoother animation
        }
        navController.popBackStack()
    }



    LazyColumn(modifier = Modifier.fillMaxSize()){
        item{
            // Show a progress bar at the top
            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                color = ElevatedMustard2,
                modifier = Modifier.fillMaxWidth().height(6.dp)
            )
        }
        item {
            LazyRow (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.animateContentSize()){
                item { IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, null, tint = ElevatedMustard2)
                }}

                item { BoldSubheading(text = "Seen by: ",
                    color = ElevatedMustard2,
                    modifier = Modifier.padding(vertical = 4.dp)
                )}

                story?.let{
                    it.seenBy?.let { seenList->
                        var seenString = ""
                        seenList.forEach { uid->
                            seenString += "$uid, "
                        }
                        item { Subheading(
                            text = seenString,
                            color = ElevatedMustard2,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )}
                    }
                }
            }
        }

        item {
            story?.let {
                AsyncImage(
                    model = it.url,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}