package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class VentanaAjustes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ajustes()
        }
    }
}

@Composable
fun Ajustes() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Ajustes", fontSize = 30.sp)

        SettingItem("Cuenta") { /* Acción */ }
        SettingItem("Tiendas") { /* Acción */ }
        SettingItem("Personalización") { /* Acción */ }
        SettingItem("Privacidad") { /* Acción */ }
        SettingItem("Eliminar cuenta") { /* Acción */ }
    }
}

@Composable
fun SettingItem(text: String, onClick: () -> Unit) {
    val next = painterResource(id = R.drawable.next)
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text)
            Image(
                painter = next,
                contentDescription = "Next",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
@Preview
@Composable
fun AjustesPreview() {
    Ajustes()
}