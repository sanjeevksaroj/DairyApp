package com.skrj.dairyapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skrj.dairyapp.viewmodel.OtpViewModel
import com.skrj.dairyapp.viewmodel.NameViewModel

@Composable
fun OtpScreen(
    onOtpVerified: () -> Unit,
    viewModel: OtpViewModel = viewModel(),
    nameViewModel: NameViewModel
) {

    val otp by viewModel.otp
    val isLoading by viewModel.isLoading
    val timer by viewModel.timer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Enter OTP")

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = otp,
            onValueChange = viewModel::onOtpChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.verifyOtp {
                    // transfer app token to NameViewModel before navigating
                    val appToken = viewModel.token.value
                    if (!appToken.isNullOrBlank()) {
                        nameViewModel.setToken(appToken)
                    }
                    onOtpVerified()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Verify OTP")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (timer > 0) {
            Text("Resend in $timer sec")
        } else {
            TextButton(onClick = { viewModel.resendOtp() }) {
                Text("Resend OTP")
            }
        }
    }
}