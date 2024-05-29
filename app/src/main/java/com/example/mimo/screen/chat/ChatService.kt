package com.example.mimo.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

data class ChatRequest(val msg: String)
data class ChatResponse(val message: String)

interface ChatService {
    @POST("your/api/endpoint")
    fun sendMessage(@Body request: ChatRequest): Call<ChatResponse>
}

object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: ChatService by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8080/model") // 실제 API 기본 URL로 대체
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ChatService::class.java)
    }
}
