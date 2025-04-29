package com.example.tsgapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RB : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Result()
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Result() {
    var htmlContent by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    if (isLoading) {
        Text("Cargando página...")
    } else {
        if (htmlContent.isEmpty()) {
            Button(onClick = {
                isLoading = true
                CoroutineScope(Dispatchers.Main).launch {
                    htmlContent = getHtmlContent()
                    isLoading = false
                }
            }) {
                Text("Mostrar HTML")
            }
        } else {
            AndroidView(factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadData(htmlContent, "text/html", "UTF-8")
                }
            })
        }
    }
}

suspend fun getHtmlContent(): String {
    val client = HttpClient()
    return client.get("https://www.dia.es/search?q=").bodyAsText()
}

@Preview
@Composable
fun PreviewResult() {
    Result()
}
