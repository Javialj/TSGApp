package com.example.tsgapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsgapp.ui.theme.Printgris
import com.example.tsgapp.ui.theme.SelectedField
import com.example.tsgapp.ui.theme.UnselectedField
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(auth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cpassword by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Printgris)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Text("Email", color = Color.White, fontWeight = FontWeight.Bold)
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = UnselectedField,
                    focusedContainerColor = SelectedField
                )
            )
        }
        Spacer(Modifier.height(48.dp))
        Row {
            Text("Password", color = Color.White, fontWeight = FontWeight.Bold)
            TextField(
                value = password, onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = UnselectedField,
                    focusedContainerColor = SelectedField
                )
            )
        }
        Spacer(Modifier.height(48.dp))
        Row {
            Text("Confirm Password", color = Color.White, fontWeight = FontWeight.Bold)
            TextField(
                value = cpassword, onValueChange = { cpassword = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = UnselectedField,
                    focusedContainerColor = SelectedField
                )
            )
        }
        if (password != cpassword && cpassword.isNotEmpty()) {
            Text(
                text = "Las contraseñas no coinciden",
                color = Color.Red,
                fontSize = 12.sp
            )
        }
        Spacer(Modifier.height(48.dp))
        Button(onClick = {
            // Validaciones
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Log.e("Registro", "Correo inválido")
                return@Button
            }

            if (password.length < 6) {
                Log.e("Registro", "La contraseña debe tener al menos 6 caracteres")
                return@Button
            }

            if (password != cpassword) {
                Log.e("Registro", "Las contraseñas no coinciden")
                return@Button
            }

            // Registro en Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("Registro", "Usuario creado correctamente")
                    } else {
                        Log.e("Registro", "Error al crear cuenta: ${task.exception?.message}", task.exception)
                    }
                }
        }) {
            Text(text = "Sign Up")
        }
    }
}
