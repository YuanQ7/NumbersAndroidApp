package com.example.numbers.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numbers.repository.NumbersRepository
import com.example.numbers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumbersViewModel @Inject constructor(
    private val repository: NumbersRepository
) : ViewModel() {

    private val _mainState = MutableStateFlow("")
    val mainState = _mainState.asStateFlow()

    fun grabData(numbers: String) {
        viewModelScope.launch {
            val response = repository.getFromNumbers(numbers, "trivia")

            when (response) {
                is Resource.Error -> {
                    val x = 0
                }
//                    mainState.value.error = true
                is Resource.Success -> {
                    Log.d("testing", "${response.data}")
//                    mainState.value.error = false
//                    mainState.value.text = "${response.data}"
                    _mainState.value = "${response.data}"
                }
            }
        }
    }

}