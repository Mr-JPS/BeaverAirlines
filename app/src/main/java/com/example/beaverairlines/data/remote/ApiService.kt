package com.example.beaverairlines.data.remote


import com.example.beaverairlines.data.model.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "TO DO"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FlightApiService {
    @GET("TO DO")
    suspend fun getData(): ApiResponse
}

object LHApi {
    val retrofitService: FlightApiService by lazy { retrofit.create(FlightApiService::class.java) }
}