package com.example.drinkapp.screen.admin.delivering

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
 * Coroutine-based DeliveringPresenter with Hilt DI
 */
class DeliveringPresenterCoroutine @Inject constructor(
    private val repository: OrderRepository
) : DeliveringContract.Presenter, CoroutineScope {

    private var view: DeliveringContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: DeliveringContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getListOrder(order_status: Long) {
        launch {
            when (val result = repository.getAllOrderByStatus(order_status)) {
                is Result.Success -> {
                    view?.onGetListOrderSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetListOrderFail()
                }
                is Result.HttpError -> {
                    view?.onGetListOrderFail()
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
