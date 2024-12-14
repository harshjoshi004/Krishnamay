package com.example.krishnamaya1.aboutPage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.krishnamaya1.BoldSubheading
import com.example.krishnamaya1.Heading
import com.example.krishnamaya1.R
import com.example.krishnamaya1.Subheading
import com.example.krishnamaya1.ui.theme.BackgroundMustard
import com.example.krishnamaya1.ui.theme.ElevatedMustard1
import com.example.krishnamaya1.ui.theme.ElevatedMustard2

data class Contributor(val name: String, val des: String, @DrawableRes val photo: Int)

@Composable
fun AboutUs(){
    LazyColumn(modifier = Modifier.fillMaxSize()) {

//        item {
//            Spacer(Modifier.size(16.dp))
//        }

        item {
            PersonProfileCard(
                name = "Harsh Joshi",
                college = "The LNM Institute of Information Technology",
                description = "Passionate Software Engineering student with a keen interest in Native Mobile Development and Design",
                profileImageUrl = R.drawable.harsh_joshi,
                emailUrl = "mailto:john.doe@example.com",
                linkedInUrl = "https://linkedin.com/in/johndoe",
                githubUrl = "https://github.com/johndoe"
            )
        }

//        item {
//            Divider(color = ElevatedMustard2, modifier =  Modifier.padding(vertical = 16.dp))
//        }

        item {
            PersonProfileCard(
                name = "Sri Gaursundar Prabhu",
                college = null,
                description = "This app is inspired by the wisdom and compassion of Sri Gaursundar Prabhu. His teachings, blending ancient knowledge with daily life, serve as a guiding light, fostering clarity and growth on the spiritual path.",
                profileImageUrl = R.drawable.hg,
                emailUrl = null,
                linkedInUrl = null,
                githubUrl = null
            )
        }
    }
}