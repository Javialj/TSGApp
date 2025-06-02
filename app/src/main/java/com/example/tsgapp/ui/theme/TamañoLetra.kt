package com.example.tsgapp.ui.theme

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit

object TamañoLetra {
    var tamañoFuente by mutableStateOf(20)

    fun saveFontSize(context: Context){
        val sharedFont = context.getSharedPreferences("font_pref", Context.MODE_PRIVATE)
        sharedFont.edit {
            putInt("is_font_size", tamañoFuente)
        }
    }

    fun loadFont(context: Context){
        val sharedFont = context.getSharedPreferences("font_pref", Context.MODE_PRIVATE)
        tamañoFuente = sharedFont.getInt("is_font_size", 20)
    }
}