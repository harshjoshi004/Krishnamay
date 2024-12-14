package com.example.krishnamaya1.stories

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.krishnamaya1.BoldSubheading
import com.example.krishnamaya1.R
import com.example.krishnamaya1.Subheading
import com.example.krishnamaya1.addPost.presentation.AddPostViewModel
import com.example.krishnamaya1.authentication.presentation.AuthSharedPreferences
import com.example.krishnamaya1.myLoaderDialogue
import com.example.krishnamaya1.ui.theme.ElevatedMustard1
import com.example.krishnamaya1.ui.theme.ElevatedMustard2
import com.google.firebase.auth.FirebaseAuth
import com.example.krishnamaya1.ui.theme.Krishnamaya1Theme
import org.jetbrains.annotations.Async

@Composable
fun AddVideoStoryScreen(navController: NavController) {
    val context = LocalContext.current
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    val storiesViewModel: StoriesViewModel = viewModel()
    val progressDialog = myLoaderDialogue(context = context)

    // post button click logic
    val buttonClick: ()->Unit  = {
        if (videoUri == null) {
            Toast.makeText(context, "No media selected", Toast.LENGTH_SHORT).show()
        } else {
            progressDialog.show()

            storiesViewModel.addStory(
                url = videoUri!!,
                type = "video",
                onSuccess = {
                    videoUri = null
                    Toast.makeText(context, "Successfully Posted!", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                    navController.popBackStack()
                },
                onFailure = { str ->
                    Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            )
        }
    }

    // select video from gallery setup
    val permissionToGrant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        android.Manifest.permission.READ_MEDIA_VIDEO
    else
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    val videoInputLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        videoUri = uri
    }
    val permissionThenVideoInputLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("Permission", "Register: Permission Granted")
            Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show()
            videoInputLauncher.launch("video/*")
        } else {
            Log.d("Permission", "Register: Permission Denied")
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    val totalVideoInputFun: () -> Unit = {
        val havePermission = ContextCompat.checkSelfPermission(
            context,
            permissionToGrant
        ) == PackageManager.PERMISSION_GRANTED
        if (havePermission) {
            videoInputLauncher.launch("video/*")
        } else {
            permissionThenVideoInputLauncher.launch(permissionToGrant)
        }
    }

    // entire ui
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        tint = ElevatedMustard2,
                        painter = painterResource(id = com.example.krishnamaya1.R.drawable.close),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                BoldSubheading(text = "Add Story", color = ElevatedMustard2)
            }
        }

        item {
            if (videoUri != null) {
                Column (modifier = Modifier.fillMaxWidth()) {
                    Row (modifier = Modifier.padding(8.dp)){
                        BoldSubheading(text = "Video Path: ")
                        Text(videoUri.toString())
                    }

                    TextButton(
                        modifier = Modifier
                            .padding(8.dp),
                        onClick = {
                            videoUri = null
                        }
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = com.example.krishnamaya1.R.drawable.close),
                                contentDescription = null
                            )
                            Text(" Discard ")
                        }
                    }
                }
            } else {
                //add video button
                IconButton(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = {
                        totalVideoInputFun()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.attach_file),
                        contentDescription = null
                    )
                }
            }
        }

        item {
            Button(onClick = { buttonClick() },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                BoldSubheading(text = "Post")
            }
        }
    }
}

@Composable
fun AddImageStoryScreen(navController: NavController) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val storiesViewModel: StoriesViewModel = viewModel()
    val progressDialog = myLoaderDialogue(context = context)

    // post button click logic
    val buttonClick: ()->Unit  = {
        if (imageUri == null) {
            Toast.makeText(context, "No media selected", Toast.LENGTH_SHORT).show()
        } else {
            progressDialog.show()

            storiesViewModel.addStory(
                url = imageUri!!,
                type = "image",
                onSuccess = {
                    imageUri = null
                    Toast.makeText(context, "Successfully Posted!", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                    navController.popBackStack()
                },
                onFailure = { str ->
                    Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            )
        }
    }

    // select image from gallery setup
    val permissionToGrant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        android.Manifest.permission.READ_MEDIA_IMAGES
    else
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    val imageInputLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val permissionThenImageInputLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("Permission", "Register: Permission Granted")
            Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show()
            imageInputLauncher.launch("image/*")
        } else {
            Log.d("Permission", "Register: Permission Denied")
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    val totalImageInputFun: () -> Unit = {
        val havePermission = ContextCompat.checkSelfPermission(
            context,
            permissionToGrant
        ) == PackageManager.PERMISSION_GRANTED
        if (havePermission) {
            imageInputLauncher.launch("image/*")
        } else {
            permissionThenImageInputLauncher.launch(permissionToGrant)
        }
    }

// entire ui
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item{
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        tint = ElevatedMustard2,
                        painter = painterResource(id = com.example.krishnamaya1.R.drawable.close),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                BoldSubheading(text = "Add Story", color = ElevatedMustard2)
            }
        }

        item {
            if (imageUri != null) {
                //attached image
                Box(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = imageUri,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .padding(8.dp)
                            .height(250.dp)
                            .align(Alignment.Center),
                        contentDescription = null
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd),
                        onClick = {
                            imageUri = null
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = com.example.krishnamaya1.R.drawable.close),
                            contentDescription = null
                        )
                    }

                }
            } else {
                //add media
                IconButton(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = {
                        totalImageInputFun()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.attach_file),
                        contentDescription = null
                    )
                }
            }
        }

        item {
            Button(onClick = { buttonClick() },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                BoldSubheading(text = "Post")
            }
        }
    }
}