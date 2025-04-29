package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment

class ActivityVentanaPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VentanaPrincipal()
        }
    }
}

@Composable
fun VentanaPrincipal(modifier: Modifier = Modifier) {

    Box(
        modifier.fillMaxSize()

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BarraS()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun VentanaPrincipalPreview() {
    VentanaPrincipal()
}
