package com.example.drinkapp.screen.client.order

import com.example.drinkapp.data.repository.OrderRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based OrderPresenter with Hilt DI
 */
class OrderPresenterCoroutine @Inject constructor(
    private val repository: OrderRepository
) : OrderContract.Presenter, CoroutineScope {

    private var view: OrderContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: OrderContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getOrder(user_id: Long) {
        launch {
            when (val result = repository.getOrderByUserId(user_id)) {
                is Result.Success -> {
                    view?.onGetAllOrderSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetAllOrderFail()
                }
                is Result.HttpError -> {
                    view?.onGetAllOrderFail()
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
