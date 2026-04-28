package com.skrj.dairyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skrj.dairyapp.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DairyApp()
        }
    }
}

@Composable
fun DairyApp() {

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AppNavHost()
        }
    }
}
