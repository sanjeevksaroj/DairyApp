package com.skrj.dairyapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skrj.dairyapp.viewmodel.NameViewModel

@Composable
fun NameScreen(
    onNameSaved: (String) -> Unit,
    viewModel: NameViewModel = viewModel()
) {

    val name by viewModel.name
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Enter Your Name")

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = viewModel::onNameChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveName {
                    onNameSaved(viewModel.name.value)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Continue")
            }
        }
    }
}