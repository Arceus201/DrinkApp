package com.example.drinkapp.utils.base

interface BasePresenter<T> {
    fun attachView(view: T)
    fun detachView()
    fun onStart()
    fun onStop()
}