package com.example.numbers.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NumbersApi {
    @GET("{numbers}/{type}?json")
    suspend fun getFromNumbers(
        @Path("numbers") numbers: String,
        @Path("type") type: String
    ): String
}
