package com.example.drinkapp.screen.admin.order_manager

import com.example.drinkapp.data.repository.OrderRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based OrderManagerPresenter with Hilt DI
 */
class OrderManagerPresenterCoroutine @Inject constructor(
    private val repository: OrderRepository
) : OrderManagerContract.Presenter, CoroutineScope {

    private var view: OrderManagerContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: OrderManagerContract.View) {
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

    override fun updateStatusOrder(id: Long, order_status: Long) {
        val orderStatusDTO = OrderStatusDTO(order_status)
        launch {
            when (val result = repository.updateOrderStatus(id, orderStatusDTO)) {
                is Result.Success -> {
                    view?.onUpadteStatusOrderSuccess()
                }
                is Result.Error -> {
                    view?.onFail(MESS)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS)
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

    companion object {
        const val MESS = "xác nhận đơn hàng không thành công"
    }
}
