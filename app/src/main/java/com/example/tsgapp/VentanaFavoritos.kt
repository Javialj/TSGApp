package com.example.tsgapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

class VentanaFavoritos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {

        }
    }
}

@Composable
fun Favoritos() {

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween) {


        }

    }
}

@Preview
@Composable
fun PreviewFavoritos(){
    Favoritos()
}