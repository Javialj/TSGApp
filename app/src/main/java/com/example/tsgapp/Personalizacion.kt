package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Personalizacion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AjustesPersonalizados()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesPersonalizados() {
    var checked by remember { mutableStateOf(true) }
    var sliderPosition by remember { mutableFloatStateOf(15f) }
    val purple = Color(0xFF6446AB)
    val lightPurple = Color(0xFF8673B4)
    var mostrarMenu by remember { mutableStateOf(false) }
    var idiomaSeleccionado by remember { mutableStateOf("Español") }

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Small Top App Bar")
        },
        modifier = Modifier.fillMaxWidth()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 55.dp)
    ) {

        // Switch para tema
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
        ) {
            Text("Tema de la aplicación:", fontSize = 20.sp)
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                thumbContent = if (checked) {
                    {
                        Image(
                            painter = painterResource(id = R.drawable.moon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    {
                        Image(
                            painter = painterResource(id = R.drawable.sun),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            )
        }

        // Slider para tamaño de letra
        Text("Tamaño de la letra:", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Valor: ${sliderPosition.toInt()}", fontSize = 15.sp)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 15f..50f,
                steps = 5,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Blue,
                    inactiveTrackColor = Color.Gray
                ),
                track = { sliderSizes ->
                    Canvas(modifier = Modifier.fillMaxWidth()) {
                        drawRoundRect(
                            color = Color.Gray,
                            size = Size(size.width, 4.dp.toPx()),
                            cornerRadius = CornerRadius(2.dp.toPx())
                        )
                        val normalizedValue = (sliderPosition - 15f) / (50f - 15f)
                        val filledWidth = normalizedValue * size.width
                        drawRoundRect(
                            color = lightPurple,
                            size = Size(filledWidth, 4.dp.toPx()),
                            cornerRadius = CornerRadius(2.dp.toPx())
                        )
                    }
                },
                thumb = {
                    val size = 20.dp
                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(9.dp, purple)
                    )
                }
            )
        }

        // Menú de idioma (TopAppBar con DropdownMenu)
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp) // Altura recomendada por Material3
                .clickable { mostrarMenu = true }, // Hacer toda la fila clickeable
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Idioma:", fontSize = 20.sp)
            Box {
                // TopAppBar solo muestra el título y el botón
                TopAppBar(
                    title = { Text(idiomaSeleccionado) },
                    modifier = Modifier
                        .width(150.dp)
                        .height(30.dp),
                    actions = {
                        IconButton(onClick = { mostrarMenu = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.down),
                                contentDescription = "Opciones"
                            )
                        }
                    }
                )

                // DropdownMenu fuera del TopAppBar pero dentro del Box
                DropdownMenu(
                    expanded = mostrarMenu,
                    onDismissRequest = { mostrarMenu = false },
                    modifier = Modifier.width(150.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Español", fontSize = 20.sp) },
                        onClick = {
                            idiomaSeleccionado = "Español"
                            mostrarMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Inglés", fontSize = 20.sp) },
                        onClick = {
                            idiomaSeleccionado = "Inglés"
                            mostrarMenu = false
                        }
                    )
                }
            }
        }

        // Botón Guardar
        Spacer(modifier = Modifier.weight(1f)) // Espaciador flexible
        Button(
            onClick = { /*TODO()*/ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Guardar")
        }
    }
}


@Preview
@Composable
fun PreviewAjustesP(){
    AjustesPersonalizados()
}