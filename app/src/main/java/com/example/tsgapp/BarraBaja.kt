package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class BarraBaja : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarraDespla()
        }
    }
}

@Composable
fun BarraDespla(){
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {/*TODO()*/},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favorite),
                contentDescription = null
            )
        }

        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 5.dp),
            color = Color.Gray
        )

        Button(
            onClick = {/*TODO()*/},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = null
            )
        }

        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 5.dp),
            color = Color.Gray
        )

        Button(
            onClick = {/*TODO()*/},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewBarra(){
    BarraDespla()
}