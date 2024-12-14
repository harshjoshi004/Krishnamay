package com.example.krishnamaya1.stories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.krishnamaya1.BoldSubheading
import com.example.krishnamaya1.Subheading
import com.example.krishnamaya1.authentication.data.KrishnamayaUser
import com.example.krishnamaya1.ui.theme.ElevatedMustard1
import com.example.krishnamaya1.ui.theme.ElevatedMustard2
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BrowseStories(navController: NavController) {
    val storiesViewModel: StoriesViewModel = viewModel()
    var listState by remember { mutableStateOf<List<Pair<Story, KrishnamayaUser>>?>(null) }

    // Load data initially
    LaunchedEffect(Unit) {
        storiesViewModel.getStories({ listState = it }, {})
    }

    val userClick:(Story)->Unit = {
        storiesViewModel.seeStory(it.storyId,{
            if(it.type == "image") {
                navController.navigate("story-presenter/${it.storyId}")
            } else {
                navController.navigate("story-presenter2/${it.storyId}")
            }
        },{})
    }
    val refresh = {
        storiesViewModel.getStories({ listState = it }, {})
    }

    LazyRow(Modifier.fillMaxWidth()) {
        if(listState == null) {
            item {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(com.example.krishnamaya1.R.drawable.defimg),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .padding(start = 8.dp)
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Subheading(text = " Click above to \nadd Stories..", color = ElevatedMustard2)
                }
            }
        } else {
            if(listState!!.isEmpty()){
                item {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Image(
                            painter = painterResource(com.example.krishnamaya1.R.drawable.defimg),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(start = 8.dp)
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                        Subheading(text = " Click above to \nadd Stories..", color = ElevatedMustard2)
                    }
                }
            } else {
                listState!!.forEach { (story, user) ->
                    item {
                        AsyncImage(
                            model = user.imageLink,
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(start = 8.dp)
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(color = ElevatedMustard1)
                                .clickable { userClick(story) }
                        )
                    }
                }
            }
        }
    }
}