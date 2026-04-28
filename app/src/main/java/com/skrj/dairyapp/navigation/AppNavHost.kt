package com.skrj.dairyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skrj.dairyapp.ui.login.LoginScreen
import com.skrj.dairyapp.ui.login.OtpScreen
import com.skrj.dairyapp.ui.login.NameScreen
import com.skrj.dairyapp.ui.dashboard.DashboardScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        // Login
        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Otp.route)
                }
            )
        }

        // OTP
        composable(Routes.Otp.route) {
            OtpScreen(
                onOtpVerified = {
                    navController.navigate(Routes.Name.route)
                }
            )
        }

        // Name
        composable(Routes.Name.route) {
            NameScreen(
                onNameSaved = { name ->   // 👈 receive name
                    navController.navigate(
                        Routes.Dashboard.createRoute(name)
                    ) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Dashboard
        composable(Routes.Dashboard.route) {
            DashboardScreen()
        }
    }
}