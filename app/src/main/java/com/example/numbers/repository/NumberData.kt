package com.example.numbers.repository

import java.util.Collections
import kotlin.random.Random


class NumberData {

    val dataMap: MutableMap<Int, MutableSet<String>> = mutableMapOf()

    fun randomEntry() : Pair<Int, String> {
        val entry = dataMap.entries.elementAt(Random.nextInt(dataMap.size))

        val num = entry.key
        val text = entry.value.random()

        return Pair(num, text)
    }
}