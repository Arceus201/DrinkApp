package com.example.drinkapp.screen.client.history

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
 * Coroutine-based HistoryPresenter with Hilt DI
 */
class HistoryPresenterCoroutine @Inject constructor(
    private val repository: OrderRepository
) : HistoryContract.Presenter, CoroutineScope {

    private var view: HistoryContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: HistoryContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getHistoryOrder(user_id: Long) {
        launch {
            when (val result = repository.getHistoryOrderByUserId(user_id)) {
                is Result.Success -> {
                    view?.onGetAllHistoryOrderSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetAllHistoryOrderFail()
                }
                is Result.HttpError -> {
                    view?.onGetAllHistoryOrderFail()
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
