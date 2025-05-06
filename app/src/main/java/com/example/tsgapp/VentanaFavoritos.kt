package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class VentanaFavoritos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
            favoritos()
        }
    }
}

@Composable
fun favoritos() {
    Box(){
        Column(modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text("Aqui deben ir los favoritos")
        }
    }
}