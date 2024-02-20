package com.example.numbers.repository

import java.util.Collections

class NumberData {

    val dataMap: MutableMap<Int, MutableSet<String>> = Collections.synchronizedMap(mutableMapOf())

}