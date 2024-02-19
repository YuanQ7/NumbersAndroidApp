package com.example.numbers.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numbers.repository.NumbersRepository
import com.example.numbers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumbersViewModel @Inject constructor(
    private val repository: NumbersRepository
) : ViewModel() {

    private val _textState = MutableStateFlow("")
    val textState = _textState.asStateFlow()

    private val _errorState = MutableStateFlow(false)
    val errorState = _errorState.asStateFlow()

    fun grabData(numbers: String) {
        viewModelScope.launch {
            val response = repository.getFromNumbers(numbers, "trivia")

            when (response) {
                is Resource.Success -> {
                    Log.d("testing", "${response.data}")

                    _errorState.value = false
                    _textState.value = "${response.data}"
                }
                else -> {
                    // Resource.Error
                    _errorState.value = true
                }
            }
        }
    }

}