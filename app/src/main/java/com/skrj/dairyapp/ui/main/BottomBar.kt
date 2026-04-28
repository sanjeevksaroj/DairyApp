package com.skrj.dairyapp.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.skrj.dairyapp.navigation.Routes

@Composable
fun BottomBar(navController: NavHostController) {

    val items = listOf(
        Routes.Home,
        Routes.Wallet,
        Routes.Orders
    )

    NavigationBar {

        val currentRoute =
            navController.currentBackStackEntry?.destination?.route

        items.forEach { route ->

            NavigationBarItem(
                selected = currentRoute == route.route,
                onClick = {
                    navController.navigate(route.route) {
                        popUpTo(Routes.Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = when (route) {
                            Routes.Home -> Icons.Default.Home
                            Routes.Wallet -> Icons.Default.AccountBalanceWallet
                            Routes.Orders -> Icons.Default.List
                            else -> Icons.Default.Home
                        },
                        contentDescription = route.route
                    )
                },
                label = {
                    Text(route.route)
                }
            )
        }
    }
}