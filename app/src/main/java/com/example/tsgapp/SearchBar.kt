package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

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

    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = { newQuery ->
            query = newQuery
        },
        onSearch = {
            println("Búsqueda realizada: $query")
        },
        active = false,
        onActiveChange = {
        },
        placeholder = { Text("Buscar...") },
        leadingIcon = {R.drawable.search},
        trailingIcon = {}
    ) {

    }
}



@Preview
@Composable
fun PreviewBarra() {
    BarraS()
}