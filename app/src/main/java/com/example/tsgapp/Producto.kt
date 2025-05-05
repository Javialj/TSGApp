package com.example.tsgapp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URLEncoder

data class Producto(val nombre: String, val precio: String, val imagenUrl: String, val ofertaproducto: String, val ofertaprecio: String)

suspend fun getProductosDIA(query: String): List<Producto> {
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")
    return try {
        val url = "https://www.dia.es/search?q=$encodedQuery"
        val document = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }

        val items = document.select(".search-product-card")

        items.mapNotNull { element ->

            val nombre = element.selectFirst(".search-product-card__product-name")?.text().orEmpty()
            val precio = element.selectFirst(".search-product-card__active-price")?.text().orEmpty()
            val imgElement = element.selectFirst(".search-product-card__product-image")
            val imageUrl = imgElement?.absUrl("src").orEmpty()
            val ofertaproducto = element.selectFirst(".product-special-offer-promotion-title")?.text().orEmpty()
            val ofertaprecio = element.selectFirst(".product-special-offer-discount-percentage-strikethrough-price")?.text().orEmpty()

            if (nombre.isNotBlank() && imageUrl.isNotBlank()) {
                Producto(nombre, precio, imageUrl, ofertaproducto, ofertaprecio)
            } else {
                null
            }
        }
    } catch (e: Exception) {
        Log.e("JsoupError", "Error parsing HTML", e)
        emptyList()
    }
}

suspend fun getProductosAhorramas(query: String): List<Producto> {
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")
    return try {
        val url = "https://www.ahorramas.com/buscador?q=$encodedQuery"
        val document = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }

        val items = document.select(".product-tile")

        items.mapNotNull { element ->
            val nombre = element.selectFirst(".product-name-gtm")?.text().orEmpty()

            val precio = element.selectFirst(".price .value")?.text().orEmpty()

            val imgElement = element.selectFirst(".tile-image")
            val imageUrl = imgElement?.absUrl("src").orEmpty()

            val ofertaproducto = element.selectFirst(".tile-promotions")?.text().orEmpty()
            val ofertaprecio = element.selectFirst(".unit-price-per-unit")?.text().orEmpty()

            if (nombre.isNotBlank() && imageUrl.isNotBlank()) {
                Producto(nombre, precio, imageUrl, ofertaproducto, ofertaprecio)
            } else {
                null
            }
        }
    } catch (e: Exception) {
        Log.e("JsoupError", "Error parsing HTML", e)
        emptyList()
    }
}




//https://tienda.mercadona.es/search-results?query=
//https://www.carrefour.es/?query=
//https://www.lidl.es/es/search?query=
