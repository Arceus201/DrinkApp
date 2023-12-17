package com.example.drinkapp.screen.admin.product_size

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiPriceSize
import com.example.drinkapp.data.resource.call.CallApiSize

class ProductSizePresenter(
    private val view: ProductSizeContract.View,
    private val callApiSize: CallApiSize,
    private val callApi : CallApiPriceSize
) :
    ProductSizeContract.Presenter {
    override fun getALLSize() {
        callApiSize.getAllSize(
            object : OnResultListener<List<Size>> {
                override fun onSuccess(list: List<Size>) {
                    if (list != null && list.isNotEmpty()) {
                        val data: List<Pair<Long, String>> = list
                            .filter { it.id != null && it.name != null }
                            .map { it.id!! to it.name!! }
                        view.onGetAllSizeSuccess(data)
                    }
                }

                override fun onFail(message: String) {

                }
            }
        )
    }

    override fun getAllPriceSize(idProduct: Long ) {
        callApi.getAllPriceSizeByProductID(
            idProduct,
            object : OnResultListener<List<PriceSize>>{
                override fun onSuccess(list: List<PriceSize>) {
                    view.onGetAllPriceSizeSucess(list)
                }

                override fun onFail(message: String) {
                    view.onGetAllPriceSizeFail()
                }

            }
        )
    }

    override fun addPriceSize(idProduct: Long, idSize: Long, price: Double) {
        callApi.addPriceSize(
            idProduct,idSize,price,
            object : OnResultListener<List<PriceSize>>{
                override fun onSuccess(list: List<PriceSize>) {
                    view.onUpdateAndAddSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESSAGE_ADD_PRICESIZE_FAIL)
                }

            }
        )
    }

    override fun updatePriceSize(id: Long, idProduct: Long, idSize: Long, price: Double,status: Long) {
        callApi.updatePriceSize(
            id,idProduct,idSize,price,status,
            object : OnResultListener<List<PriceSize>>{
                override fun onSuccess(list: List<PriceSize>) {
                    view.onUpdateAndAddSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESSAGE_UPADTE_PRICESIZE_FAIL)
                }
            }
        )
    }

    override fun onStart() {
        // TODO("Not yet implemented")
    }

    override fun onStop() {
        //TODO("Not yet implemented")
    }
    companion object{
        const val MESSAGE_ADD_PRICESIZE_FAIL = "thêm giá theo size không thành công"
        const val MESSAGE_UPADTE_PRICESIZE_FAIL = "cập nhật giá theo size không thành công"
    }

}