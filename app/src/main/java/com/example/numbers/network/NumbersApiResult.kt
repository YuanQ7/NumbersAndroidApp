package com.example.numbers.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
{
 "text": "42 is the number of spots (or pips, circular patches or pits) on a pair of standard six-sided dice.",
 "number": 42,
 "found": true,
 "type": "trivia"
}
 */

//@JsonClass(generateAdapter = true)
data class NumbersApiResult (
//    @Json(name = "text") val text: String,
//    @Json(name = "number") val number: Int,
//    @Json(name = "found") val found: Boolean,
//    @Json(name = "type") val type: String
    val results: Map<String, Any> = HashMap()
)