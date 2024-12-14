package com.example.krishnamaya1.contactAndSupport

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun WallpaperPage() {
    val context = LocalContext.current
    val onImageClick:(String)->Unit = {it->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
        context.startActivity(intent)
    }
    val wallpaperList = listOf(
        "https://wallpapers.com/images/high/krishna-phone-radha-painting-cow-4iz7klbeu3ja0jg1.webp",
        "https://images.pexels.com/photos/5353418/pexels-photo-5353418.jpeg?auto=compress&cs=tinysrgb&w=600",
        "https://images.pexels.com/photos/13078613/pexels-photo-13078613.jpeg?auto=compress&cs=tinysrgb&w=600",
        "https://wallpapers.com/images/high/krishna-phone-gold-figurine-jfye1y3tenivdqcl.webp",
        "https://wallpapers.com/images/high/krishna-phone-radha-looking-at-candle-zz5zqp9kd7kpti5o.webp",
        "https://wallpapers.com/images/high/krishna-phone-baby-in-butter-vase-e44qnv7vhu1rjrza.webp",
        "https://wallpapers.com/images/high/krishna-phone-playing-flute-by-waterfall-55994uyjwmzbicrv.webp",
    )

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),

        content = {
            items(wallpaperList) { photo ->
                AsyncImage(
                    model = photo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5))
                        .clickable{onImageClick(photo)}
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}