package com.example.tsgapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    var query by mutableStateOf("")
    var productosDIA by mutableStateOf<List<Producto>>(emptyList())
    var productosAhorramas by mutableStateOf<List<Producto>>(emptyList())
    var productosCarrefour by mutableStateOf<List<Producto>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun updateQuery(newQuery: String) {
        query = newQuery
    }

    fun searchProducts() {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                productosDIA = getProductosDIA(query)
                productosAhorramas = getProductosAhorramas(query)
                productosCarrefour = getProductosMercadona(query)
            } catch (e: Exception) {
                errorMessage = "Error al buscar productos"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadInitialProducts() {
        val listacomienzo = listOf("Mazana", "Galletas", "Carne", "Refresco")
        val productoAleatorio = listacomienzo.random()
        viewModelScope.launch {
            productosDIA = getProductosDIA(productoAleatorio)
            productosAhorramas = getProductosAhorramas(productoAleatorio)
        }
    }
}