package com.skrj.dairyapp.ui.login

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import com.skrj.dairyapp.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    val phone by viewModel.phone
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Enter Phone Number")

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = phone,
            onValueChange = viewModel::onPhoneChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val activity = LocalContext.current as Activity

        Button(
            onClick = {
                viewModel.onLoginClicked(activity) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Send OTP")
            }
        }
    }
}