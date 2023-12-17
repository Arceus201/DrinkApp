package com.example.drinkapp.data.resource

interface OnResultListener<T> {
    fun onSuccess(list: T)
    fun onFail(message: String)
}