package com.example.drinkapp.utils.base

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Base presenter implementing IBasePresenter and CoroutineScope.
 * Provides lifecycle-aware coroutine management and view attachment.
 */
abstract class BasePresenter<T> : IBasePresenter<T>, CoroutineScope {
    
    private var _view: T? = null
    
    // Property for subclasses to access view - NOT protected to avoid compiler issues
    val view: T?
        get() = _view
    
    private val job = SupervisorJob()
    
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleCoroutineException(throwable)
    }
    
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler
    
    override fun attachView(view: T) {
        this._view = view
    }
    
    override fun detachView() {
        _view = null
    }
    
    override fun onStart() {
        // Override in subclasses if needed
    }
    
    override fun onStop() {
        job.cancel()
        detachView()
    }
    
    private fun handleCoroutineException(throwable: Throwable) {
        // Log exception for non-cancellation exceptions
        if (throwable !is kotlinx.coroutines.CancellationException) {
            Log.e("BasePresenter", "Coroutine exception", throwable)
        }
    }
}
