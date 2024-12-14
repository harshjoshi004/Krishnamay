package com.example.krishnamaya1.aboutPage

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.krishnamaya1.ui.theme.ElevatedMustard1
import com.example.krishnamaya1.ui.theme.ElevatedMustard2
import com.example.krishnamaya1.R

@Composable
fun PersonProfileCard(
    name: String,
    college: String?,
    description: String,
    @DrawableRes profileImageUrl: Int,
    emailUrl: String? = null,
    linkedInUrl: String? = null,
    githubUrl: String? = null
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF773923),
                        Color(0xFF381500)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Profile Image
                Image(
                    painter = painterResource(profileImageUrl),
                    contentDescription = "$name's profile picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(ElevatedMustard1)
                        .border(4.dp, Color(0xFFee2a7b), CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.size(16.dp))

                Column() {
                    // Name
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // College
                    if (college != null) {
                        Text(
                            text = college,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = description,
                textAlign = TextAlign.Center,
                color = ElevatedMustard1,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Social Links
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (emailUrl != null) {
                    SocialButton(
                        icon = R.drawable.gmail,
                        contentDescription = "Email",
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:")
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("theharshjoshi2@gmail.com"))
                                putExtra(Intent.EXTRA_SUBJECT, "Add a subject..")
                                putExtra(Intent.EXTRA_TEXT, "Tell Me Something..")
                            }
                            context.startActivity(intent)
                        }
                    )
                }

                if (linkedInUrl != null) {
                    SocialButton(
                        icon = R.drawable.linkedin, // Replace with actual LinkedIn icon
                        contentDescription = "LinkedIn",
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.linkedin.com/in/harsh-joshi-6a0509257")
                            )
                            context.startActivity(intent)
                        }
                    )
                }

                if (githubUrl != null) {
                    SocialButton(
                        icon = R.drawable.github, // Replace with actual GitHub icon
                        contentDescription = "GitHub",
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/harshjoshi004")
                            )
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SocialButton(
    @DrawableRes icon: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(
                Color.White.copy(alpha = 0.2f),
                shape = CircleShape
            )
            .size(48.dp)
    ) {
        Icon(
            painterResource(icon),
            contentDescription = contentDescription,
            tint = ElevatedMustard1,
            modifier = Modifier.size(36.dp)
        )
    }
}