package com.example.drinkapp.utils.base

interface IBasePresenter<T> {
    fun attachView(view: T)
    fun detachView()
    fun onStart()
    fun onStop()
}
