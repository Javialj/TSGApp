@file:Suppress("DEPRECATION")

package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
            Row {
                SearchBar()
                Image(
                    painter = painterResource(id = R.drawable.smartprice),
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }

    // Implementa la barra de búsqueda utilizando SearchBar
    SearchBar(
        query = query,
        onQueryChange = { newQuery ->
            query = newQuery  // Actualiza el estado con el nuevo valor de búsqueda
        },
        onSearch = {
            // Define lo que sucede cuando se realiza una búsqueda
            println("Búsqueda realizada: $query")
        },
        active = false, // Controla si la barra de búsqueda está activa o no
        onActiveChange = {
            // Acciones cuando cambia el estado activo/inactivo de la barra
        },
        placeholder = { Text("Buscar...") }, // Placeholder de la barra de búsqueda
        leadingIcon = {}, // Ícono opcional al principio (por ejemplo, lupa)
        trailingIcon = {} // Ícono opcional al final (por ejemplo, cancelar)
    ) {
        // Aquí podrías mostrar sugerencias o resultados mientras el usuario escribe
    }
}

@Preview(showBackground = true)
@Composable
fun VentanaPrincipalPreview() {
    VentanaPrincipal()
}
