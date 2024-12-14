package com.example.krishnamaya1.contactAndSupport

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.outlined.AttachEmail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.krishnamaya1.BoldSubheading
import com.example.krishnamaya1.CommonButton
import com.example.krishnamaya1.Heading
import com.example.krishnamaya1.Subheading
import com.example.krishnamaya1.ui.theme.ElevatedMustard1
import com.example.krishnamaya1.ui.theme.ElevatedMustard2

@Composable
fun ContactAndSupportUI(){
    val context = LocalContext.current
    val emailString = "theharshjoshi2@gmail.com"
    val googleFormUrl = "https://forms.gle/HRkoFZ7gAZQcyqNNA"

    val onFormClick: () -> Unit = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleFormUrl))
        context.startActivity(intent)
    }

    val onEmailClick: () -> Unit = {
        // Start email intent
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailString))
            putExtra(Intent.EXTRA_SUBJECT, "Add a subject..")
            putExtra(Intent.EXTRA_TEXT, "Tell Me Something..")
        }
        context.startActivity(intent)
    }
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ){
        item {
            BoldSubheading(
                text = "Welcome to support",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
        }
        item {
            Heading(
                text = "How can we help you?",
                color = ElevatedMustard2,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
        }
        item {
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .clickable { onEmailClick() }
                    .border(2.dp, ElevatedMustard2, RoundedCornerShape(10))
            ){
                Icon(
                    imageVector = Icons.Outlined.AttachEmail,
                    contentDescription = "Email Icon",
                    tint = ElevatedMustard2,
                    modifier = Modifier
                        .size(55.dp)
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row (modifier = Modifier.padding(16.dp)){
                    Text(
                        text = "Email us at ",
                        color = ElevatedMustard2,
                    )
                    Text(
                        text = emailString,
                        fontWeight = FontWeight.Bold,
                        color = ElevatedMustard2,
                    )
                }

            }
        }
        item{
            BoldSubheading(
                text = "Get in touch with us :",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
        item{
            CommonButton(
                text = "Fill the Form",
                onClick = onFormClick,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
        }
        item {
            Subheading(
                text = "Click the button to share your thoughts with us! A quick Google Form awaits, and we'll be in touch ASAP",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}