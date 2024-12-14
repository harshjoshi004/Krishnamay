package com.example.krishnamaya1.stories

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.krishnamaya1.BoldSubheading
import com.example.krishnamaya1.Subheading
import com.example.krishnamaya1.ui.theme.ElevatedMustard2
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.delay

@Composable
fun StoryPresenter2(storyId: String?, navController: NavController) {
    val context = LocalContext.current
    val storiesViewModel: StoriesViewModel = viewModel()
    var story by remember { mutableStateOf<Story?>(null) }
    var progress by remember { mutableStateOf(1f) }

    // Create ExoPlayer instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    // Cleanup ExoPlayer when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(Unit) {
        storiesViewModel.getStoryById(storyId!!,
            onSuccess = { retrievedStory ->
                story = retrievedStory
                // Once we have the story, set up the video
                story?.url?.let { videoUrl ->
                    val mediaItem = MediaItem.fromUri(videoUrl)
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    exoPlayer.play()
                }
            },
            onFailure = {}
        )
    }

    // Handle progress and navigation
    LaunchedEffect(exoPlayer) {
        while (true) {
            if (exoPlayer.duration > 0) {
                progress = 1f - (exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat())
                if (exoPlayer.currentPosition >= exoPlayer.duration) {
                    navController.popBackStack()
                    break
                }
            }
            delay(50)
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                color = ElevatedMustard2,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )
        }

        item {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.animateContentSize()
            ) {
                item {
                    IconButton(onClick = {
                        exoPlayer.stop()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, null, tint = ElevatedMustard2)
                    }
                }

                item {
                    BoldSubheading(
                        text = "Seen by: ",
                        color = ElevatedMustard2,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                story?.let { currentStory ->
                    currentStory.seenBy?.let { seenList ->
                        var seenString = ""
                        seenList.forEach { uid ->
                            seenString += "$uid, "
                        }
                        item {
                            Subheading(
                                text = seenString,
                                color = ElevatedMustard2,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f / 16f) // Standard video aspect ratio
            ) {
                AndroidView(
                    factory = { context ->
                        StyledPlayerView(context).apply {
                            player = exoPlayer
                            useController = false // Hide default controls
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}