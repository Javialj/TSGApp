package com.example.tsgapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class Cuenta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VentanaCuenta()
        }
    }
}

@Composable
fun VentanaCuenta(navController: NavController? = null ) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    var email by remember { mutableStateOf("Cargando...") }
    var creationDate by remember { mutableStateOf("Cargando...") }

    // Cargar datos del usuario
    LaunchedEffect(Unit) {
        if (user != null) {
            email = user.email ?: "No disponible"
            val creationTimestamp = user.metadata?.creationTimestamp

            creationDate = if (creationTimestamp != null) {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(Date(creationTimestamp))
            } else {
                "Desconocida"
            }

            saveUserData(context, email, creationDate)
        } else {
            navController?.navigate("welcome")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Información de la cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        UserInfoItem(label = "Correo electrónico", value = email)
        UserInfoItem(label = "Fecha de registro", value = creationDate)

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            // Opcional: cerrar sesión
            auth.signOut()
            navController?.navigate("cuenta")
        }) {
            Text(text = "Cerrar sesión")
        }
    }
}

fun saveUserData(context: Context, email: String, creationDate: String) {
    val sharedPref = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("user_email", email)
        putString("user_creation_date", creationDate)
        apply()
    }
}

@Composable
fun UserInfoItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}