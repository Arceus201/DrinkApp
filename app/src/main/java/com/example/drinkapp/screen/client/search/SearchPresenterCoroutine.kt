package com.example.drinkapp.screen.client.search

import com.example.drinkapp.data.repository.ProductRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based SearchPresenter with Hilt DI
 */
class SearchPresenterCoroutine @Inject constructor(
    private val repository: ProductRepository
) : SearchContract.Presenter, CoroutineScope {

    private var view: SearchContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: SearchContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllProduct() {
        launch {
            when (val result = repository.getAllProductsClient()) {
                is Result.Success -> {
                    view?.onGetAllProductSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail()
                }
                is Result.HttpError -> {
                    view?.onFail()
                }
            }
        }
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }
}
