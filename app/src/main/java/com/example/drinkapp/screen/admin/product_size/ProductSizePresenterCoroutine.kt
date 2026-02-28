package com.example.drinkapp.screen.admin.product_size

import com.example.drinkapp.data.repository.PriceSizeRepository
import com.example.drinkapp.data.repository.SizeRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based ProductSizePresenter with Hilt DI
 */
class ProductSizePresenterCoroutine @Inject constructor(
    private val sizeRepository: SizeRepository,
    private val priceSizeRepository: PriceSizeRepository
) : ProductSizeContract.Presenter, CoroutineScope {

    private var view: ProductSizeContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ProductSizeContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getALLSize() {
        launch {
            when (val result = sizeRepository.getAllSizes()) {
                is Result.Success -> {
                    val sizes = result.data
                    if (sizes.isNotEmpty()) {
                        val data: List<Pair<Long, String>> = sizes
                            .filter { it.id != null && it.name != null }
                            .map { it.id!! to it.name!! }
                        view?.onGetAllSizeSuccess(data)
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

    override fun getAllPriceSize(idProduct: Long) {
        launch {
            when (val result = priceSizeRepository.getAllPriceSizeByProductID(idProduct)) {
                is Result.Success -> {
                    view?.onGetAllPriceSizeSucess(result.data)
                }
                is Result.Error -> {
                    view?.onGetAllPriceSizeFail()
                }
                is Result.HttpError -> {
                    view?.onGetAllPriceSizeFail()
                }
            }
        }
    }

    override fun addPriceSize(idProduct: Long, idSize: Long, price: Double) {
        launch {
            when (val result = priceSizeRepository.addPriceSize(idProduct, idSize, price)) {
                is Result.Success -> {
                    view?.onUpdateAndAddSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESSAGE_ADD_PRICESIZE_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(result.message)
                }
            }
        }
    }

    override fun updatePriceSize(id: Long, idProduct: Long, idSize: Long, price: Double, status: Long) {
        launch {
            when (val result = priceSizeRepository.updatePriceSize(id, idProduct, idSize, price, status)) {
                is Result.Success -> {
                    view?.onUpdateAndAddSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESSAGE_UPDATE_PRICESIZE_FAIL)
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
        const val MESSAGE_ADD_PRICESIZE_FAIL = "thêm giá theo size không thành công"
        const val MESSAGE_UPDATE_PRICESIZE_FAIL = "cập nhật giá theo size không thành công"
    }
}
