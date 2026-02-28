package com.example.drinkapp.screen.client.cart

import com.example.drinkapp.data.repository.CartItemRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based CartPresenter with Hilt DI
 */
class CartPresenterCoroutine @Inject constructor(
    private val repository: CartItemRepository
) : CartContract.Presenter, CoroutineScope {

    private var view: CartContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: CartContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllCartItemByUserID(user_id: Long) {
        launch {
            when (val result = repository.getAllCartItemByUserID(user_id)) {
                is Result.Success -> {
                    view?.onDisplayAllCartSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onDisplayAllCartFail()
                }
                is Result.HttpError -> {
                    view?.onDisplayAllCartFail()
                }
            }
        }
    }

    override fun updateCartItemNumber(id: Long, number: Long) {
        launch {
            when (val result = repository.updateCartItemNumber(id, number)) {
                is Result.Success -> {
                    view?.onUpdateCartNumberSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESS_UPDATE_CART_ITEM_NUMBER_ERRROR)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_UPDATE_CART_ITEM_NUMBER_ERRROR)
                }
            }
        }
    }

    override fun deleteCartItemById(id: Long) {
        launch {
            when (val result = repository.deleteCartItemById(id)) {
                is Result.Success -> {
                    view?.onDeleteCartItemByIdSuccess(id)
                }
                is Result.Error -> {
                    view?.onFail(MESSAGE_DELETE_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESSAGE_DELETE_FAIL)
                }
            }
        }
    }

    override fun deleteAll(user_id: Long) {
        launch {
            when (val result = repository.deleteAll(user_id)) {
                is Result.Success -> {
                    view?.onDeleteAllSuccess()
                }
                is Result.Error -> {
                    view?.onFail(MESSAGE_DELETE_ALL_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESSAGE_DELETE_ALL_FAIL)
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
        const val MESSAGE_DELETE_ALL_FAIL = "xóa tất cả sản phẩm lỗi"
        const val MESSAGE_DELETE_FAIL = "xóa ản phẩm lỗi"
        const val MESS_UPDATE_CART_ITEM_NUMBER_ERRROR = "cập nhật số lượng sản phẩm lỗi"
    }
}
