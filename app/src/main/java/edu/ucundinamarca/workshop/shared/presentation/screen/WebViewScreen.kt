package edu.ucundinamarca.workshop.shared.presentation.screen

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String, modifier: Modifier = Modifier) {
    var webView: WebView? by remember { mutableStateOf(null) }
    var canGoBack by remember { mutableStateOf(false) }

    BackHandler(enabled = canGoBack) {
        webView?.goBack()
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    builtInZoomControls = true
                    displayZoomControls = false
                    setSupportZoom(true)
                    loadsImagesAutomatically = true
                }

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        canGoBack = view?.canGoBack() ?: false
                    }
                }

                webChromeClient = WebChromeClient()

                setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP && canGoBack) {
                        goBack()
                        true
                    } else {
                        false
                    }
                }

                loadUrl(if (url.startsWith("http")) url else "https://$url")
                webView = this
            }
        },
        update = { view ->
            // Evitar recargar si la URL es la misma
            if (view.url != url && view.url != "https://$url") {
                view.loadUrl(if (url.startsWith("http")) url else "https://$url")
            }
        },
        modifier = modifier.fillMaxSize()
    )
}
