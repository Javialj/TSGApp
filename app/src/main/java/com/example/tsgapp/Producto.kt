package com.example.tsgapp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URLEncoder

data class Producto(val nombre: String, val precio: String, val imagenUrl: String)

suspend fun getProductos(query: String): List<Producto> {
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")
    return try {
        val url = "https://www.dia.es/search?q=$encodedQuery"
        val document = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }

        val items = document.select(".search-product-card")

        items.map { element ->
            val nombre = element.selectFirst(".search-product-card__product-name")?.text() ?: ""
            val precio = element.selectFirst(".search-product-card__active-price")?.text() ?: ""

            val imgElement = element.selectFirst(".search-product-card__product-image")
            val imageUrl = imgElement?.absUrl("src") ?: ""

            Producto(nombre, precio, imageUrl)
        }
    } catch (e: Exception) {
        Log.e("JsoupError", "Error parsing HTML", e)
        emptyList()
    }
}