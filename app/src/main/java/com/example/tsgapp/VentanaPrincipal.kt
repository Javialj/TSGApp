package com.example.tsgapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.tsgapp.ui.theme.TamañoLetra
import com.example.tsgapp.ui.theme.ThemeState
import kotlinx.coroutines.launch

class VentanaPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Principal()
        }
    }
}

@SuppressLint("UnrememberedMutableState")
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
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )  {
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {
                        coroutineScope.launch {
                            isLoading = true
                            productosDIA = getProductosDIA(query)
                            productosAhorramas = getProductosAhorramas(query)
                            productosCarrefour = getProductosMercadona(query)
                            isLoading = false
                        }
                    },
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text("Buscar...") },
                    trailingIcon = { /* Opcional: agrega un icono de limpiar búsqueda */ },
                    modifier = Modifier
                        .weight(1f),
                    enabled = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp,
                    windowInsets = SearchBarDefaults.windowInsets,
                    interactionSource = remember { MutableInteractionSource() },
                    content = {}
                )
                if (!ThemeState.isDarkMode){
                    Image(
                        modifier = Modifier.size(80.dp).padding(top = 25.dp),
                        painter = painterResource(R.drawable.smartprice),
                        contentDescription = null
                    )
                } else {
                    Image(
                        modifier = Modifier.size(80.dp).padding(top = 25.dp),
                        painter = painterResource(R.drawable.smartpricedark),
                        contentDescription = null
                    )
                }

            }
            if (!ThemeState.isDarkMode){
                Divider(modifier = Modifier.padding(top = 16.dp), color = Color.Black)
            } else {
                Divider(modifier = Modifier.padding(top = 16.dp), color = Color.White)
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
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
                    if (productosDIA.isEmpty() && productosAhorramas.isEmpty()){
                        ProductosInicio()
                    }
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
                            Text("")
                        } else {
                            Column {
                                if (productosDIA.isNotEmpty()) {
                                    Row (
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val contexto = LocalContext.current
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("Productos DIA", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
                                            Button(
                                                modifier = Modifier.size(80.dp),
                                                onClick = { Toast.makeText(contexto, "Puede variar el precio en tienda", Toast.LENGTH_SHORT).show()},
                                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.alert),
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                        MarcarFavo {  }
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
                                            Text("Productos DIA", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
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
                                        Text("Productos Ahorramas", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
                                        MarcarFavo {  }
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
                                                fontSize = TamañoLetra.tamañoFuente.sp
                                            )
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
                                        Text("Productos Carrefour", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
                                        MarcarFavo {  }
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
                                                fontSize = TamañoLetra.tamañoFuente.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text("No se ha encontrado resultados en este supermercado", fontSize = TamañoLetra.tamañoFuente.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductosInicio(){
    var productosDIA by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productosAhorramas by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productosCarrefour by remember { mutableStateOf<List<Producto>>(emptyList()) }

    val listacomienzo = listOf("Mazana", "Galletas", "Carne", "Refresco")
    val productoAleatorio = listacomienzo.random()
    val contexto = LocalContext.current
    LaunchedEffect(Unit) {
        productosDIA = getProductosDIA(productoAleatorio)
        productosAhorramas = getProductosAhorramas(productoAleatorio)
    }
    Column {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Productos DIA", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
                Button(
                    modifier = Modifier.size(80.dp),
                    onClick = { Toast.makeText(contexto, "Puede variar el precio en tienda", Toast.LENGTH_SHORT).show()},
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(R.drawable.alert),
                        contentDescription = null
                    )
                }
            }
            MarcarFavo {  }
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
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Productos Ahorramas", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
            MarcarFavo {  }
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
    }
}

@Composable
fun MarcarFavo(onClick: () -> Unit){
    if (!ThemeState.isDarkMode){
        Button(
            onClick = {onClick},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favoritep),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    } else {
        Button(
            onClick = {onClick},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favoritepdark),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
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
                    fontSize = TamañoLetra.tamañoFuente.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = producto.nombre,
                    fontSize = TamañoLetra.tamañoFuente.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (producto.ofertaprecio.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.ofertaprecio,
                    fontSize = TamañoLetra.tamañoFuente.sp
                )
            }
            if (producto.ofertaproducto.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    textAlign = TextAlign.Right,
                    text = producto.ofertaproducto,
                    fontSize = 14.sp
                )
            }
            if (producto.ofertaespe.isNotBlank()){
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.ofertaespe,
                    fontSize = TamañoLetra.tamañoFuente.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = producto.precio,
                fontWeight = FontWeight.Bold,
                fontSize = TamañoLetra.tamañoFuente.sp
            )
        }
    }
}

@Preview
@Composable
fun PrincipalPreview(){
    Principal()
}