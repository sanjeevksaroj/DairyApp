package com.skrj.dairyapp.navigation

sealed class Routes(val route: String) {
    object Login : Routes("login")
    //object Dashboard : Routes("dashboard")
    object Otp : Routes("otp")
    object Name : Routes("name")
    object Dashboard : Routes("dashboard/{name}") {
        fun createRoute(name: String) = "dashboard/$name"
    }

    object Main : Routes("main/{name}") {
        fun createRoute(name: String) = "main/$name"
    }

    // Bottom tabs
    object Home : Routes("home")
    object Wallet : Routes("wallet")
    object Orders : Routes("orders")

}