package com.example.drinkapp.screen.client.home

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
 * Coroutine-based HomePresenter with Hilt DI
 */
class HomePresenterCoroutine @Inject constructor(
    private val repository: ProductRepository
) : HomeContract.Presenter, CoroutineScope {

    private var view: HomeContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getProductHot() {
        launch {
            when (val result = repository.getProductHot()) {
                is Result.Success -> {
                    view?.onGetProductHotSuccess(result.data)
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
