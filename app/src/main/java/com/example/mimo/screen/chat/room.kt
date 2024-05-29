package com.example.dreaminterpretationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DreamInterpretationApp(navController = NavHostController)
        }
    }
}

@Composable
fun DreamInterpretationApp(navController: NavHostController) {
    var dreamContent by remember { mutableStateOf(TextFieldValue("")) }
    var dreamInterpretation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = dreamContent,
            onValueChange = { dreamContent = it },
            label = { Text("꿈 내용") },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                val client = OkHttpClient()
                val requestBody = JSONObject().put("msg", dreamContent.text).toString()
                val mediaType = "application/json; charset=utf-8".toMediaType()
                val request = Request.Builder()
                    .url("http://localhost:8080/model")
                    .post(RequestBody.create(mediaType, requestBody))  // Use mediaType here
                    .build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val json = JSONObject(response.body!!.string())
                    dreamInterpretation = json.getString("message")
                } else {
                    dreamInterpretation = "꿈 해몽 실패"
                }
            },

            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("꿈 해몽")
        }

        Text(
            text = dreamInterpretation,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
