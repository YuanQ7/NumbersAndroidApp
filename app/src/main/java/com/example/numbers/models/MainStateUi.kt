package com.example.numbers.models

import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
data class MainStateUi (
    var text: String = "",
    var error: Boolean = false
)