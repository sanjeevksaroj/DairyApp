package com.skrj.dairyapp.navigation

sealed class Routes(val route: String) {
    object Login : Routes("login")
    //object Dashboard : Routes("dashboard")
    object Otp : Routes("otp")
    object Name : Routes("name")
    object Dashboard : Routes("dashboard/{name}") {
        fun createRoute(name: String) = "dashboard/$name"
    }

}