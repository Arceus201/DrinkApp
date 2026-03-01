package com.example.drinkapp.screen.admin.product

import com.example.drinkapp.data.repository.ProductRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based ProductPresenter with Hilt DI
 */
class ProductPresenterCoroutine @Inject constructor(
    private val repository: ProductRepository
) : ProductContract.Presenter, CoroutineScope {

    private var view: ProductContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ProductContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllDrink() {
        launch {
            when (val result = repository.getAllProducts()) {
                is Result.Success -> {
                    view?.showAllDrinkSuccess(result.data)
                }
                is Result.Error -> {
                    view?.showAllDrinkFail()
                }
                is Result.HttpError -> {
                    view?.showAllDrinkFail()
                }
            }
        }
    }

    override fun updateProduct(
        id: Long,
        name: String,
        imageUri: String,
        price: Double,
        statusCode: Long,
        cateId: Long
    ) {
        launch {
            val product = com.example.drinkapp.data.model.Product(
                id = id,
                name = name,
                image = imageUri,
                price = price,
                status = statusCode,
                quantitysold = 0,
                note = null,
                category = com.example.drinkapp.data.model.Category(cateId, "")
            )
            when (val result = repository.updateProduct(id, product)) {
                is Result.Success -> {
                    view?.onUpdateSuccess(MESS_UPDATE_STATUS_SUCCESS)
                }
                is Result.Error -> {
                    view?.onFail(MESS_UPDATE_STATUS_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(result.message)
                }
            }
        }
    }

    override fun deleteProduct(id: Long) {
        launch {
            when (val result = repository.deleteProduct(id)) {
                is Result.Success -> {
                    view?.onDeleteProductSuccess(MESS_DELETE_SUCCESS)
                }
                is Result.Error -> {
                    view?.onFail(MESS_DELETE_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(result.message)
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
        const val MESS_UPDATE_STATUS_SUCCESS = "cập nhật trạng thái sản phẩm thành công"
        const val MESS_UPDATE_STATUS_FAIL = "cập nhật trạng thái sản phẩm không thành công"
        const val MESS_DELETE_FAIL = "xóa sản phẩm không thành công"
        const val MESS_DELETE_SUCCESS = "xóa sản phẩm thành công"
    }
}
