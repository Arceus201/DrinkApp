package com.example.drinkapp.screen.admin.product_update

import com.example.drinkapp.data.repository.CategoryRepository
import com.example.drinkapp.data.repository.ProductRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based ProductUpdatePresenter with Hilt DI
 */
class ProductUpdatePresenterCoroutine @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ProductUpdateContract.Presenter, CoroutineScope {

    private var view: ProductUpdateContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ProductUpdateContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllCategory() {
        launch {
            when (val result = categoryRepository.getAllCategories()) {
                is Result.Success -> {
                    val categories = result.data
                    if (categories.isNotEmpty()) {
                        val data: List<Pair<Long, String>> = categories
                            .filter { it.id != null && it.name != null }
                            .map { it.id!! to it.name!! }
                        view?.displayAllCategory(data)
                    }
                }
                is Result.Error -> {
                    // Handle error silently as per original implementation
                }
                is Result.HttpError -> {
                    // Handle error silently as per original implementation
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
            when (val result = productRepository.updateProduct(id, product)) {
                is Result.Success -> {
                    view?.onUpdateSuccess()
                }
                is Result.Error -> {
                    view?.onFail(MESS_UPDATE_PRODUCT_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(result.message)
                }
            }
        }
    }

    override fun updatePriceSizeDefault(idProduct: Long, idSize: Long, price: Double, status: Long) {
        // Note: This method is not migrated as it requires PriceSizeRepository
        // which is not injected in the constructor based on the requirements
    }

    override fun checkInputUpdate(name: String, imageUri: String, price: String) {
        if (name.isNullOrEmpty() or imageUri.isNullOrEmpty() or price.isNullOrEmpty()) {
            view?.onFail(MESS_CHECK_INPUT_UPDATE_FAIL)
        } else {
            view?.onCheckInputUpdateSuccess(imageUri)
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
        const val MESS_UPDATE_PRODUCT_SUCSESS = "cập nhật sản phẩm thành công"
        const val MESS_UPDATE_PRODUCT_FAIL = "cập nhật sản phẩm không thành công"
        const val MESS_CHECK_INPUT_UPDATE_FAIL = "Bạn cần nhập đầy đủ thông tin trước khi nhấn update"
    }
}
