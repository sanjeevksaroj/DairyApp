package com.skrj.dairyapp.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(name: String) {

    Column(Modifier.padding(16.dp)) {

        Text("Welcome, $name 👋")

        Spacer(modifier = Modifier.height(16.dp))

        Card {
            Column(Modifier.padding(16.dp)) {
                Text("Wallet: ₹500")
                Text("Next Delivery: Tomorrow")
            }
        }
    }
}