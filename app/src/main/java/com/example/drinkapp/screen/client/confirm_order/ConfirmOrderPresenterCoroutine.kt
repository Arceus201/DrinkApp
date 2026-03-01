package com.example.drinkapp.screen.client.confirm_order

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.repository.AddressRepository
import com.example.drinkapp.data.repository.CartItemRepository
import com.example.drinkapp.data.repository.ExchangeRateRepository
import com.example.drinkapp.data.repository.OrderRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.payment.PaymentDTO
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Coroutine-based ConfirmOrderPresenter with Hilt DI
 */
class ConfirmOrderPresenterCoroutine @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartItemRepository: CartItemRepository,
    private val addressRepository: AddressRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) : BasePresenter<ConfirmOrderContract.View>(), ConfirmOrderContract.Presenter {

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
        launch {
            when (val result = exchangeRateRepository.getExchangeRate()) {
                is Result.Success -> {
                    view?.onGetExChangeRateSuccess(result.data)
                }
                is Result.Error -> {
                    // Silent fail as per original implementation
                }
                is Result.HttpError -> {
                    // Silent fail as per original implementation
                }
            }
        }
    }

    companion object {
        const val MESS_ORDER_FAIL = "đặt hàng không thành công"
    }
}
