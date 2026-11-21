package com.example.eva2.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL_UBICACIONES = "https://x8ki-letl-twmt.n7.xano.io/api:YZ-QO7NG/"
    private const val BASE_URL_USUARIOS = "https://x8ki-letl-twmt.n7.xano.io/api:0gHlK1Up/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val ubicacionesApi: UbicacionesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_UBICACIONES)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UbicacionesApi::class.java)
    }

    val usuariosApi: UsuariosApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_USUARIOS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuariosApi::class.java)
    }
}