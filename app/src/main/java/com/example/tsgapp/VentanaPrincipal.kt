package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

class VentanaPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Principal()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Principal() {
    var query by remember { mutableStateOf("") }
    var productosDIA by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productosAhorramas by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productosCarrefour by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    coroutineScope.launch {
                        isLoading = true
                        productosDIA = getProductosDIA(query)
                        productosAhorramas = getProductosAhorramas(query)
                        productosCarrefour = getProductosCarrefour(query)
                        isLoading = false
                    }
                },
                active = false,
                onActiveChange = {},
                placeholder = { Text("Buscar...") },
                trailingIcon = { /* Opcional: agrega un icono de limpiar búsqueda */ },
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                shape = MaterialTheme.shapes.medium,
                colors = SearchBarDefaults.colors(),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                windowInsets = SearchBarDefaults.windowInsets,
                interactionSource = remember { MutableInteractionSource() },
                content = {}
            )
            Divider(modifier = Modifier.padding(top = 16.dp))
        }
        Box(
            modifier = Modifier
                .weight(1f) // Ahora sí funciona, porque está dentro de un Column
                .fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        if (productosDIA.isEmpty() && productosAhorramas.isEmpty()) {
                            Text("No se encontraron productos.")
                        } else {
                            Column {
                                if (productosDIA.isNotEmpty()) {
                                    Row (
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Productos DIA", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                        Button(
                                            onClick = {/**/},
                                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.favoritep),
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
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
                                } else {
                                    Column {
                                        Row (
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("Productos DIA", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                            Button(
                                                onClick = {/**/},
                                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.favoritep),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text("No se ha encontrado resultados en este supermercado")
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                if (productosAhorramas.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row (
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Productos Ahorramas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                        Button(
                                            onClick = {/**/},
                                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.favoritep),
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
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
                                } else {
                                    Column {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "Productos Ahorrmas",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                            Button(
                                                onClick = {/**/ },
                                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.favoritep),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text("No se ha encontrado resultados en este supermercado")
                                    }
                                }
                                if (productosCarrefour.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row (
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Productos Carrefour", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                        Button(
                                            onClick = {/**/},
                                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.favoritep),
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    LazyRow(
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp)
                                    ) {
                                        items(productosCarrefour.size) { index ->
                                            ProductoItem(producto = productosCarrefour[index])
                                        }
                                    }
                                } else {
                                    Column {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "Productos Carrefour",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                            Button(
                                                onClick = {/**/ },
                                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.favoritep),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text("No se ha encontrado resultados en este supermercado")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            BarraDespla()
        }
    }
}


@Composable
fun ProductoItem(producto: Producto) {
    var expandido by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { expandido = !expandido },
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

@Preview
@Composable
fun PrincipalPreview(){
    Principal()
}