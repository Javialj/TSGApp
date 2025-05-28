package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

class VentanaFavoritos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {

        }
    }
}

@Composable
fun Favoritos() {
    val context = LocalContext.current
    val favorites = FavoritesRepository.loadFavorites(context)

    if (favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tienes productos favoritos")
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(favorites.size) { index ->
                val producto = favorites[index]
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Row(modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                        .data(producto.imagenUrl)
                        .build(),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                            Text(producto.nombre, fontWeight = FontWeight.Bold)
                            Text("Precio: ${producto.precio}")
                        }
                        // Botón para eliminar
                        Button(
                            onClick = { FavoritesRepository.toggleFavorite(context, producto) },
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFavoritos(){
    Favoritos()
}