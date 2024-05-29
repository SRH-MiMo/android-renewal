package com.example.mimo.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.example.mimo.data.RequestData
import com.example.mimo.data.RetrofitInstance
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

class DreamViewModel : ViewModel() {
    val responseMessage = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(false)

    fun sendDreamMessage(message: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val requestData = RequestData(msg = message)
                val response = RetrofitInstance.api.sendDream(requestData)
                responseMessage.value = response.message
            } catch (e: Exception) {
                // Handle the exception
                e.printStackTrace()
                responseMessage.value = "Error: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}

@Composable
fun DreamScreen(dreamViewModel: DreamViewModel = viewModel(), navController: NavController) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    val responseMessage = dreamViewModel.responseMessage.value
    val isLoading = dreamViewModel.isLoading.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Enter your dream message") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                dreamViewModel.sendDreamMessage(textState.text)
            },
            enabled = !isLoading,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Send")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            responseMessage?.let {
                Text(text = it)
            }
        }
    }
}