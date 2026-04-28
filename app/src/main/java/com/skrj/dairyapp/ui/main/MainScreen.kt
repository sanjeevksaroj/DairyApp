package com.skrj.dairyapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skrj.dairyapp.navigation.Routes
import com.skrj.dairyapp.ui.dashboard.DashboardScreen

@Composable
fun MainScreen(name: String) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Routes.Home.route) {
                DashboardScreen(name)
            }

            composable(Routes.Wallet.route) {
                Text("Wallet Screen")
            }

            composable(Routes.Orders.route) {
                Text("Orders Screen")
            }
        }
    }
}