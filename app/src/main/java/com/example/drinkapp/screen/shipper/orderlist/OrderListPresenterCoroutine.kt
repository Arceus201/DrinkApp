package com.example.drinkapp.screen.shipper.orderlist

import com.example.drinkapp.data.repository.OrderRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.order.OrderShipperConfirmDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based OrderListPresenter with Hilt DI
 */
class OrderListPresenterCoroutine @Inject constructor(
    private val repository: OrderRepository
) : OrderListContract.Presenter, CoroutineScope {

    private var view: OrderListContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: OrderListContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getListOrder(status: Long) {
        launch {
            when (val result = repository.getAllOrderByStatus(status)) {
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

    override fun getOrderByShipperId(shipper_id: Long) {
        launch {
            when (val result = repository.getOrderByShipperId(shipper_id)) {
                is Result.Success -> {
                    view?.onGetOrderByShipperIdSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetOrderByShipperIdFail()
                }
                is Result.HttpError -> {
                    view?.onGetOrderByShipperIdFail()
                }
            }
        }
    }

    override fun updateShippingOrder(order_id: Long, shipper_id: Long, status: Long) {
        launch {
            val orderShipperConfirmDTO = OrderShipperConfirmDTO(shipper_id, status)
            when (val result = repository.updateShippingOrder(order_id, orderShipperConfirmDTO)) {
                is Result.Success -> {
                    view?.updateShippingOrderSucess()
                }
                is Result.Error -> {
                    view?.onFail(MESSAGE_CONFIRM_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESSAGE_CONFIRM_FAIL)
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
        const val MESSAGE_CONFIRM_FAIL = "xác nhận không thành công"
    }
}
