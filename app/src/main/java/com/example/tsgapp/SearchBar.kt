package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch
import java.net.URLEncoder

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
    var htmlContent by remember { mutableStateOf("") }
    var displayHtml by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        // SearchBar
        androidx.compose.material3.SearchBar(
            query = query,
            onQueryChange = { newQuery ->
                query = newQuery
            },
            onSearch = {
                coroutineScope.launch {
                    htmlContent = getHtmlContent(query)
                    displayHtml = true
                }
            },
            active = false,
            onActiveChange = {},
            placeholder = { Text("Buscar...") },
            leadingIcon = {},
            trailingIcon = {}
        ) {
            // Aquí puedes poner sugerencias o ítems si quisieras
        }

        // Botón para mostrar el resultado
        Button(
            onClick = {
                coroutineScope.launch {
                    htmlContent = getHtmlContent(query)
                    displayHtml = true
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Mostrar Resultado")
        }

        // Mostrar el contenido HTML completo
        if (displayHtml && htmlContent.isNotEmpty()) {
            Text(
                text = "Resultado:\n$htmlContent",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight() // Ajusta este valor según tus necesidades
            )
        } else if (displayHtml) {
            Text("Cargando o sin resultados...", modifier = Modifier.padding(top = 16.dp))
        }
    }
}

//https://www.ahorramas.com/buscador?q=
//https://tienda.mercadona.es/search-results?query=
//https://www.carrefour.es/?query=
//https://www.lidl.es/es/search?query=
//https://www.compraonline.alcampo.es/search?q=

suspend fun getHtmlContent(query: String): String {
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")
    val client = HttpClient(CIO)
    return try {
        client.get("https://www.ahorramas.com/buscador?q=$encodedQuery").bodyAsText()
    } catch (e: Exception) {
        "Error al obtener el contenido: ${e.message}"
    }
}

@Preview
@Composable
fun PreviewBarra() {
    BarraS()
}