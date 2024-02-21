package com.example.numbers.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numbers.repository.NumberData
import com.example.numbers.repository.NumbersRepository
import com.example.numbers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class NumbersViewModel @Inject constructor(
    private val repository: NumbersRepository,
    private val numberData: NumberData
) : ViewModel() {
    private val _textState = MutableStateFlow("")
    val textState = _textState.asStateFlow()

    private val _errorState = MutableStateFlow(false)
    val errorState = _errorState.asStateFlow()

    private val _minNumState = MutableStateFlow("0")
    val minNumState = _minNumState.asStateFlow()

    private val _maxNumState = MutableStateFlow("Inf")
    val maxNumState = _maxNumState.asStateFlow()

    fun grabData(numbers: String) {
        viewModelScope.launch {
            val response = repository.getFromNumbers(randNumbers(1, 1000), "trivia")

            when (response) {
                is Resource.Success -> {
                    Log.d("testing", "${response.data}")
                    val data = response.data!!

                    // store data
                    storeData(data)
                    Log.d("testing", "${numberData.dataMap.size}, ${numberData.dataMap[42]}")

                    _errorState.value = false
                    _textState.value = "${response.data}"
                }
                else -> {
                    // Resource.Error
                    _errorState.value = true
//                    Log.e("testing", "${response.message}")
                }
            }
        }
    }

    private fun storeData(str: String) {
        // remove leading and trailing json
        val trim = str.substring(3, str.length - 3)
//        Log.d("testing", "str trimmed: $trim")

        val arr = trim.split("\": \"", ".\",")
        val map = numberData.dataMap

        for (i in arr.indices step 2) {
            // check for error cases
            // only keep digits for the num
//            val num = arr[i].filter {
//                it.isDigit()
//            }.toInt()
            val sep = arr[i + 1].split(" ", limit = 2)

            val num = sep[0].toInt()
            val text = sep[1]

            if (num !in map) {
                map[num] = mutableSetOf(text)
            } else {
                map[num]!!.add(text)
            }
        }
    }

    private fun randNumbers(start: Int, end: Int) : List<Int> {
        val ints = generateSequence {
            Random.nextInt(start until end)
        }
            .distinct()
            .take(100)
            .sorted()
            .toList()
        return ints
    }
}