package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tsgapp.ui.theme.TSGAppTheme
import com.example.tsgapp.ui.theme.TamañoLetra
import com.example.tsgapp.ui.theme.ThemeState
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeState.loadTheme(this)
        TamañoLetra.loadFont(this)
        FirebaseApp.initializeApp(this)
        val auth = FirebaseAuth.getInstance()
        setContent {
            TSGAppTheme {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation(auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val firebaseUser = FirebaseAuth.getInstance().currentUser
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            if (currentRoute in listOf("principal", "favoritos", "ajustes", "personalizacion", "eliminar_cuenta", "cuenta")) {
                BarraDespla(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "initial",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("initial") { InitialScreen(navController, auth) }
            composable("welcome") { InicioSes(navController) }
            composable("principal") { principal() }
            composable("favoritos") { Favoritos() }
            composable("ajustes") { Ajustes(navController) }
            composable("personalizacion") { AjustesPersonalizados() }
            composable("eliminar_cuenta") { ECuenta() }
            composable("cuenta") { VentanaCuenta( navController) }
            composable("logIn") {
                LoginScreen(navController, auth) {
                    navController.navigate("principal") {
                        popUpTo("logIn") { inclusive = true }
                    }
                }
            }
            composable("logInGmail") {
                LoginScreenGmail(navController, auth) {
                    navController.navigate("principal") {
                        popUpTo("logInGmail") { inclusive = true }
                    }
                }
            }
            composable("signUp") {
                SignUpScreen(navController, auth)
            }
        }
    }
}

@Composable
fun InitialScreen(navController: NavController, auth: FirebaseAuth) {
    val user = auth.currentUser
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate("principal") {
                popUpTo("welcome") { inclusive = true }
            }
        } else {
            navController.navigate("welcome")
        }
    }
}

@Composable
fun Ajustes(navController: NavController) {
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

            SettingItem("Cuenta") { navController.navigate("cuenta") }
            SettingItem("Personalización") { navController.navigate("personalizacion") }
            SettingItem("Eliminar cuenta") { navController.navigate("eliminar_cuenta") }

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
            Text(text, fontSize = TamañoLetra.tamañoFuente.sp)
            Image(
                painter = next,
                contentDescription = "Next",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun BarraDespla(navController: NavController? = null){

    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (!ThemeState.isDarkMode){
            Button(
                onClick = { navController?.navigate("favoritos")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.favorite),
                    contentDescription = null
                )
            }
        } else {
            Button(
            onClick = { navController?.navigate("favoritos")},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favoritedark),
                contentDescription = null
            )
        }}


        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 5.dp),
            color = Color.Gray
        )
        if (!ThemeState.isDarkMode) {
            Button(
            onClick = { navController?.navigate("principal")},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = null
            )
        }} else {
            Button(
                onClick = { navController?.navigate("principal")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.homedark),
                    contentDescription = null
                )
            }
        }


        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 5.dp),
            color = Color.Gray
        )

        if (!ThemeState.isDarkMode) {
            Button(
                onClick = { navController?.navigate("ajustes")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = null
                )
            }
        } else {
            Button(
                onClick = { navController?.navigate("ajustes")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.settingsdark),
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TSGAppTheme {}
}