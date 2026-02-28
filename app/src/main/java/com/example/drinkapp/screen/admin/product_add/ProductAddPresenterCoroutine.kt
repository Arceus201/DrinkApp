package com.example.drinkapp.screen.admin.product_add

import android.net.Uri
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
 * Coroutine-based ProductAddPresenter with Hilt DI
 */
class ProductAddPresenterCoroutine @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ProductAddContract.Presenter, CoroutineScope {

    private var view: ProductAddContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ProductAddContract.View) {
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

    override fun addProduct(
        name: String,
        imageUri: String,
        price: Double,
        statusCode: Long,
        cateId: Long
    ) {
        launch {
            when (val result = productRepository.addProduct(name, imageUri, price, statusCode, cateId)) {
                is Result.Success -> {
                    view?.onProductAdded(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESS_ADD_PRODUCT_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(result.message)
                }
            }
        }
    }

    override fun checkInputAdd(name: String, imageUri: Uri?, price: String) {
        if (name.isNullOrEmpty() or (imageUri == null) or price.isNullOrEmpty()) {
            view?.onFail(MESS_CEHCK_INPUT_ADD_FAIL)
        } else {
            view?.onCheckInputAddSuccess()
        }
    }

    override fun addPriceSize(idProduct: Long, idSize: Long, price: Double) {
        // Note: This method is not migrated as it requires PriceSizeRepository
        // which is not injected in the constructor based on the requirements
        // Original implementation has swapped success/fail messages
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }

    companion object {
        const val MESS_ADD_PRODUCT_SUCSESS = "thêm sản phẩm thành công"
        const val MESS_ADD_PRODUCT_FAIL = "thêm sản phẩm không thành công"
        const val MESS_CEHCK_INPUT_ADD_FAIL = "Bạn cần nhập đầy đủ thông tin trước khi nhấn add"
    }
}
