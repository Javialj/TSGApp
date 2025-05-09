package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Cuenta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VentanaCuenta()
        }
    }
}

@Composable
fun VentanaCuenta() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Campos de entrada
        InputField(label = "Usuario:")
        Spacer(modifier = Modifier.height(16.dp))
        InputField(label = "Contraseña:")

        // Enlace de recuperación de contraseña
        Spacer(modifier = Modifier.height(15.dp))
        RecuperarContrasena()

        // Separador con "or"
        Spacer(modifier = Modifier.height(30.dp))
        DividerWithText(text = "or")

        // Botones de inicio de sesión
        Spacer(modifier = Modifier.height(30.dp))
        LoginButton(text = "Gmail", iconRes = R.drawable.gmail)
        LoginButton(text = "Google", iconRes = R.drawable.google)
        LoginButton(text = "X", iconRes = R.drawable.twitter)
        LoginButton(text = "Facebook", iconRes = R.drawable.facebook)
    }
}

// Componente reutilizable para campos de texto
@Composable
fun InputField(label: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

// Componente para el enlace de recuperación de contraseña
@Composable
fun RecuperarContrasena() {
    Button(
        onClick = {/*TODO()*/},
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Text(
            text = "Olvidaste tu contraseña?",
            fontSize = 18.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}

// Componente para el separador con texto
@Composable
fun DividerWithText(text: String) {
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
            text = text,
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
}

// Componente reutilizable para botones con ícono
@Composable
fun LoginButton(text: String, iconRes: Int) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { /* Acción al hacer clic */ },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VentanaUsuariosPreview() {
    VentanaCuenta()
}
