package com.example.kotlinrecyclerviewexercise.model

import okhttp3.OkHttpClient
import okhttp3.Request

object SingletonClient {
    val clientInstance = OkHttpClient()
}