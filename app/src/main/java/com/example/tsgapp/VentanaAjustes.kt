package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class VentanaAjustes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ajustes()
        }
    }
}

@Composable
fun Ajustes() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Ajustes", fontSize = 30.sp, modifier = Modifier.padding(vertical = 20.dp))

            SettingItem("Cuenta") { /* Acción */ }
            SettingItem("Personalización") { /* Acción */ }
            SettingItem("Eliminar cuenta") { /* Acción */ }

            Spacer(modifier = Modifier.padding(20.dp))

            Tiendas()
        }
        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
        ){
            BarraDespla()
        }
    }
}

@Composable
fun Tiendas() {
    val selectedOptions = remember { mutableStateListOf(true, true, true) }
    val options = listOf("DIA", "Ahorramas", "Carrefour")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
        Text(
            text = "Tiendas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Gray
        )
        Divider(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }

    MultiChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                checked = selectedOptions[index],
                onCheckedChange = {
                    selectedOptions[index] = !selectedOptions[index]
                },
                icon = { SegmentedButtonDefaults.Icon(selectedOptions[index]) },
                label = {
                    when (label) {
                        "DIA" -> Text("DIA")
                        "Ahorramas" -> Text("Ahorramas")
                        "Carrefour" -> Text("Carrefour")
                    }
                }
            )
        }
    }
}

@Composable
fun SettingItem(text: String, onClick: () -> Unit) {
    val next = painterResource(id = R.drawable.next)
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text)
            Image(
                painter = next,
                contentDescription = "Next",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
@Preview
@Composable
fun AjustesPreview() {
    Ajustes()
}