package com.example.drinkapp.screen.client.order_detail

import com.example.drinkapp.data.repository.AddressRepository
import com.example.drinkapp.data.repository.OrderRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Coroutine-based OrderDetailPresenter with Hilt DI
 */
class OrderDetailPresenterCoroutine @Inject constructor(
    private val orderRepository: OrderRepository,
    private val addressRepository: AddressRepository
) : BasePresenter<OrderDetailContract.View>(), OrderDetailContract.Presenter {

    override fun getOrder(id: Long) {
        launch {
            when (val result = orderRepository.getOrder(id)) {
                is Result.Success -> {
                    view?.onGetOrderSuccess(result.data)
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

    override fun getAddressStore() {
        launch {
            when (val result = addressRepository.getAddressStore()) {
                is Result.Success -> {
                    view?.onGetAddressStoreSuccess(result.data)
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
}
