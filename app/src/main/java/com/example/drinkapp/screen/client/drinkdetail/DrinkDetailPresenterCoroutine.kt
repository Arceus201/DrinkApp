package com.example.drinkapp.screen.client.drinkdetail

import com.example.drinkapp.data.repository.CartItemRepository
import com.example.drinkapp.data.repository.PriceSizeRepository
import com.example.drinkapp.data.repository.ProductRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.formatAsNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based DrinkDetailPresenter with Hilt DI
 */
class DrinkDetailPresenterCoroutine @Inject constructor(
    private val productRepository: ProductRepository,
    private val priceSizeRepository: PriceSizeRepository,
    private val cartItemRepository: CartItemRepository
) : DrinkDetailContract.Presenter, CoroutineScope {

    private var view: DrinkDetailContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: DrinkDetailContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getProductById(id: Long) {
        launch {
            when (val result = productRepository.getProductById(id)) {
                is Result.Success -> {
                    view?.onGetProductSuccess(result.data)
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

    override fun changeQuantity(index: Long, action: Long) {
        if (index == 1L && action == -1L) {
            view?.onFail(MESS_UPDATE_CART_ITEM_NUMBER_ERRROR)
        } else {
            view?.onChangeQuantitySuccess(index + action)
        }
    }

    override fun getListPriceSize(idProduct: Long) {
        launch {
            when (val result = priceSizeRepository.getAllPriceSizeClientByProductID(idProduct)) {
                is Result.Success -> {
                    val data: List<Pair<Long, String>> = result.data
                        .filter { it.id != null && it.size.name != null }
                        .map { it.id!! to it.size.name + " Giá " + it.price.formatAsNumber() + " đ" }
                    view?.onGetListPriceSizeSuccess(data)
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

    override fun checkCartItem(user_id: Long, priseSizeId: Long) {
        launch {
            when (val result = cartItemRepository.checkCartItem(user_id, priseSizeId)) {
                is Result.Success -> {
                    view?.onCheckCartItemSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onCheckFail()
                }
                is Result.HttpError -> {
                    view?.onCheckFail()
                }
            }
        }
    }

    override fun addCartItem(pricesize_id: Long, user_id: Long, number: Long, note: String) {
        launch {
            when (val result = cartItemRepository.addCartItem(pricesize_id, user_id, number, note)) {
                is Result.Success -> {
                    view?.onAddToCartSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESS_ADD_TO_CART_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_ADD_TO_CART_FAIL)
                }
            }
        }
    }

    override fun updateCartItemNumber(id: Long, number: Long) {
        launch {
            when (val result = cartItemRepository.updateCartItemNumber(id, number)) {
                is Result.Success -> {
                    view?.onAddToCartSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESS_ADD_TO_CART_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_ADD_TO_CART_FAIL)
                }
            }
        }
    }

    override fun updateCartItemUpdate(id: Long, number: Long, note: String) {
        launch {
            when (val result = cartItemRepository.updateCartItem(id, number, note)) {
                is Result.Success -> {
                    view?.onAddToCartSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESS_ADD_TO_CART_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_ADD_TO_CART_FAIL)
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
        const val MESS_UPDATE_CART_ITEM_NUMBER_ERRROR = "số lượng sản phẩm không hợp lệ"
        const val MESS_ADD_TO_CART_FAIL = "thêm sản phẩm vào giỏ hàng lỗi"
    }
}
