package com.skrj.dairyapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skrj.dairyapp.ui.login.LoginScreen
import com.skrj.dairyapp.ui.login.OtpScreen
import com.skrj.dairyapp.ui.login.NameScreen
import com.skrj.dairyapp.ui.dashboard.DashboardScreen
import com.skrj.dairyapp.ui.main.MainScreen
import com.skrj.dairyapp.viewmodel.NameViewModel

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
            val nameViewModel: NameViewModel = viewModel()
            OtpScreen(
                onOtpVerified = {
                    navController.navigate(Routes.Name.route)
                },
                nameViewModel = nameViewModel
            )
        }

        // Name
        composable(Routes.Name.route) {
            val nameViewModel: NameViewModel = viewModel()
            NameScreen(
                onNameSaved = { name ->   // 👈 receive name
                    navController.navigate(
                        Routes.Main.createRoute(name)
                    ) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                viewModel = nameViewModel
            )
        }

        // Dashboard
        /*composable(Routes.Dashboard.route) {
            DashboardScreen()
        }*/

        // 🏠 MAIN (Bottom Navigation starts here)
        composable(Routes.Main.route) { backStackEntry ->

            val name = backStackEntry.arguments?.getString("name") ?: "User"

            MainScreen(name = name)
        }
    }
}