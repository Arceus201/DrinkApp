package com.example.drinkapp.screen.client.confirm_order

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.repository.AddressRepository
import com.example.drinkapp.data.repository.CartItemRepository
import com.example.drinkapp.data.repository.OrderRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.call.ExchangeRateAPI
import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.payment.PaymentDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based ConfirmOrderPresenter with Hilt DI
 * Note: ExchangeRateAPI still uses callback-based approach and needs separate migration
 */
class ConfirmOrderPresenterCoroutine @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartItemRepository: CartItemRepository,
    private val addressRepository: AddressRepository,
    private val callApiExchangeRate: ExchangeRateAPI // TODO: Migrate ExchangeRateAPI to coroutines
) : ConfirmOrderContract.Presenter, CoroutineScope {

    private var view: ConfirmOrderContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ConfirmOrderContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun addOrder(
        list: List<CartItem>,
        address_id: Long,
        order_time: String,
        total_price: Double,
        payment_type: Long,
        payment_status: Long
    ) {
        launch {
            val paymentDTO = PaymentDTO(payment_type, payment_status)
            val orderDTO = OrderDTO(list, address_id, paymentDTO, order_time, total_price)
            
            when (val result = orderRepository.addOrder(orderDTO)) {
                is Result.Success -> {
                    view?.onAddOrderSuccess(result.data.id)
                }
                is Result.Error -> {
                    view?.onFail(MESS_ORDER_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_ORDER_FAIL)
                }
            }
        }
    }

    override fun getExchangeRate() {
        // TODO: This method still uses callback-based ExchangeRateAPI
        // ExchangeRateAPI needs to be migrated to coroutines separately
        callApiExchangeRate.getExchangeRates(
            object : com.example.drinkapp.data.resource.OnResultListener<Double> {
                override fun onSuccess(list: Double) {
                    view?.onGetExChangeRateSuccess(list)
                }

                override fun onFail(message: String) {
                    // Silent fail as per original implementation
                }
            }
        )
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }

    companion object {
        const val MESS_ORDER_FAIL = "đặt hàng không thành công"
    }
}
