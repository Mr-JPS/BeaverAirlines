package com.example.beaverairlines.data.remote


import com.example.beaverairlines.data.model.AirportServerResponse
import com.example.beaverairlines.data.model.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val AIRPORT_BASE_URL = "https://app.goflightlabs.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(AIRPORT_BASE_URL)
    .build()

interface AirportApiService {
    @GET("airports?access_key=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiZDU5YmZiMzE2ZWQyZDVhMGE4MDVhYTYyMTNlNjg0M2JlODMyNjVkNDI4ZTU0OTUyN2RjNzM2OGVlNWU4N2M3NTA2YWM4YmRhMjI5M2RlNTAiLCJpYXQiOjE2Njg0MjczMjMsIm5iZiI6MTY2ODQyNzMyMywiZXhwIjoxNjk5OTYzMzIzLCJzdWIiOiIxODE2NCIsInNjb3BlcyI6W119.lEPlYk1VwTBlDyQB8NLu-F_CthvkL7Lvk4-epuUlJQDNBOnpFmKVW4MZmV273ofys5hJa7IHjIE14-6yzkLkBw&search")
    suspend fun getResult(@Query("search") term: String): AirportServerResponse
}

object SearchAirportApi {
    val retrofitService: AirportApiService by lazy { retrofit.create(AirportApiService::class.java) }
}