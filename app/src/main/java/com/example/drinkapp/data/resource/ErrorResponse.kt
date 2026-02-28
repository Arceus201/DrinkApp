package com.example.drinkapp.data.resource

data class ErrorResponse(
    val status: Int,
    val message: String,
    val errors: Map<String, List<String>>? = null,
    val timestamp: String? = null
)
