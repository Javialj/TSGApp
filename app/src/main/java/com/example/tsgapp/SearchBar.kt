package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

class SearchBar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarraS()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraS() {
    var query by remember { mutableStateOf("") }
    var productosDIA by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productosAhorramas by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        androidx.compose.material3.SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                coroutineScope.launch {
                    isLoading = true
                    productosDIA = getProductosDIA(query)
                    productosAhorramas = getProductosAhorramas(query)
                    isLoading = false
                }
            },
            active = false,
            onActiveChange = {},
            placeholder = { Text("Buscar...") },
            leadingIcon = {},
            trailingIcon = {}
        ) {
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (productosDIA.isEmpty() && productosAhorramas.isEmpty()) {
                Text("No se encontraron productos.", modifier = Modifier.padding(top = 16.dp))
            } else {
                Column {
                    if (productosDIA.isNotEmpty()) {
                        Text("Productos DIA", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        LazyRow(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(productosDIA.size) { index ->
                                ProductoItem(producto = productosDIA[index])
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (productosAhorramas.isNotEmpty()) {
                        Text("Productos Ahorramas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        LazyRow(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(productosAhorramas.size) { index ->
                                ProductoItem(producto = productosAhorramas[index])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto) {
    var expandido by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable{ expandido = !expandido },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.imagenUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            if (expandido) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = producto.nombre,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = producto.nombre,
                    fontSize = 14.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (producto.ofertaprecio.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.ofertaprecio,
                    fontSize = 14.sp
                )
            }

            if (producto.ofertaproducto.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.ofertaproducto,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = producto.precio,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}




