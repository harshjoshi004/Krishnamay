package com.example.krishnamaya1.krishnaStore

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun KrishnaStoreUi() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setLayerType(WebView.LAYER_TYPE_HARDWARE, null) // Enable hardware acceleration

                webViewClient = WebViewClient()
                loadUrl("https://krishnastore.in/")
            }
        }
    )
}