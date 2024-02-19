package com.example.numbers.repository

import android.util.Log
import com.example.numbers.network.NumbersApi
import com.example.numbers.network.NumbersApiResult
import com.example.numbers.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumbersRepository @Inject constructor(
    private val numbersApi: NumbersApi
){
    suspend fun getFromNumbers(
        numbers: String,
        type: String
    ) : Resource<String> {
        val response = try {
            numbersApi.getFromNumbers(numbers, type)
        } catch (e: Exception) {
            Log.d("testing", "${e.message}")
            return Resource.Error("${e.message}")
        }

        Log.d("testing", "done")

        return Resource.Success(response)
    }
}