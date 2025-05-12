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

suspend fun getProductosCorteIngles(query: String): List<Producto> {
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")
    return try {
        val url = "https://www.elcorteingles.es/supermercado/buscar/?term=$encodedQuery"

        val document = withContext(Dispatchers.IO) {
            Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "es-ES,es;q=0.9,en;q=0.8")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Referer", "https://www.google.com/ ")
                .header("Connection", "keep-alive")
                .timeout(30_000)
                .get()
        }

        val items = document.select("div.grid-item.product_tile")

        items.mapNotNull { element ->
            val nombreElement = element.selectFirst("a.js-product-link")
            val nombre = nombreElement?.attr("title") ?: nombreElement?.text().orEmpty()

            val precioElement = element.selectFirst("div.prices-price._current")
            val precio = precioElement?.text().orEmpty()
                .replace(",", ".").filter { it.isDigit() || it == '.' }

            val imgElement = element.selectFirst("img.product_tile-image")
            val imageUrl = imgElement?.absUrl("src").orEmpty()

            val ofertaproducto = ""
            val ofertaprecio = ""

            if (nombre.isNotBlank() && imageUrl.isNotBlank()) {
                Producto(nombre, precio, imageUrl, ofertaproducto, ofertaprecio)
            } else {
                null
            }
        }
    } catch (e: Exception) {
        Log.e("JsoupError", "Error parsing HTML or fetching data from El Corte Inglés", e)
        emptyList()
    }
}

//https://tienda.mercadona.es/search-results?query=
