package com.example.mimo.screen.chat

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.example.mimo.data.RequestData
import com.example.mimo.data.RetrofitInstance
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.R

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
                e.printStackTrace()
                responseMessage.value = "Error: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamScreen(dreamViewModel: DreamViewModel = viewModel(), navController: NavController) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            items(chatMessages) { message ->
                ChatMessageRow(message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Button(
                onClick = {
                    val dreamMessage = textState.text
                    if (dreamMessage.isNotBlank()) {
                        chatMessages.add(ChatMessage(ChatMessage.Type.USER, dreamMessage))
                        textState = TextFieldValue("")
                        dreamViewModel.sendDreamMessage(dreamMessage) { response ->
                            chatMessages.add(ChatMessage(ChatMessage.Type.RESPONSE, response))
                        }
                        focusManager.clearFocus() // Clear focus to hide the keyboard after sending message
                    }
                },
                contentPadding = PaddingValues(), // No padding
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(48.dp)  // Set the height of the button
                    .width(80.dp),
                shape = RoundedCornerShape(12.dp) // Rounded corners
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Up Arrow",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp) // White icon color
                )
            }

        }
    }
}

data class ChatMessage(val type: Type, val message: String) {
    enum class Type { USER, RESPONSE }
}

@Composable
fun ChatMessageRow(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (message.type == ChatMessage.Type.USER) {
            Arrangement.End // 사용자 메시지인 경우 오른쪽 정렬
        } else {
            Arrangement.Start // 응답 메시지인 경우 왼쪽 정렬
        }
    ) {
        if (message.type == ChatMessage.Type.RESPONSE) {
            Image(
                painter = painterResource(id = R.drawable.luna),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
        }
        Bubble(message.message, message.type)
    }
}


@Composable
fun Bubble(message: String, messageType: ChatMessage.Type) {
    val bubbleColor = if (messageType == ChatMessage.Type.USER) {
        Brush.horizontalGradient(colors = listOf(Color(0xFFAE00BD), Color(0xFFD11091)))
    } else {
        Brush.horizontalGradient(colors = listOf(Color(0xFF3A3A3A), Color(0xFF1A1A1A)))
    }
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.Transparent,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor)
                .padding(16.dp)
        ) {
            Text(
                text = message,
                color = if (messageType == ChatMessage.Type.USER) Color.White else Color.White,
                fontSize = 16.sp
            )
        }
    }
}

fun DreamViewModel.sendDreamMessage(message: String, onComplete: (String) -> Unit) {
    isLoading.value = true
    viewModelScope.launch {
        try {
            val requestData = RequestData(msg = message)
            val response = RetrofitInstance.api.sendDream(requestData)
            responseMessage.value = response.message
            onComplete(response.message)
        } catch (e: Exception) {
            e.printStackTrace()
            responseMessage.value = "Error: ${e.message}"
            onComplete("Error: ${e.message}")
        } finally {
            isLoading.value = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDreamScreen() {
    val navController = rememberNavController()
//    DreamScreen(dreamViewModel = DreamViewModel(), navController = navController)
}
