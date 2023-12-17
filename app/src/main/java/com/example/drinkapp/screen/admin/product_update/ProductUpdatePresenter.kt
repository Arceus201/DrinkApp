package com.example.drinkapp.screen.admin.product_update

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiCategory
import com.example.drinkapp.data.resource.call.CallApiDrink
import com.example.drinkapp.data.resource.call.CallApiPriceSize


class ProductUpdatePresenter(
    private val view: ProductUpdateContract.View,
    private val callApiCate: CallApiCategory,
    private val callApi: CallApiDrink,
    private val callApiPriceSize: CallApiPriceSize,
) :
    ProductUpdateContract.Presenter {

    override fun getAllCategory() {
        callApiCate.getAllCategory(
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    if (list != null && list.isNotEmpty()) {
                        val data: List<Pair<Long, String>> = list
                            .filter { it.id != null && it.name != null }
                            .map { it.id!! to it.name!! }
                        view.displayAllCategory(data)
                    }
                }

                override fun onFail(message: String) {
                }
            }
        )
    }

    override fun updateProduct(
        id: Long,
        name: String,
        imageUri: String,
        price: Double,
        statusCode: Long,
        cateId: Long
    ) {
        callApi.updateProduct(id, name, imageUri, price, statusCode, cateId,
            object : OnResultListener<Product> {
                override fun onSuccess(list: Product) {
                    view.onUpdateSuccess()
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_UPADTE_PRODUCT_FAIL)
                }
            })
    }

    override fun updatePriceSizeDefault( idProduct: Long, idSize: Long, price: Double,status: Long) {
        callApiPriceSize.updatePriceSizeDefault(
            idProduct, idSize, price, status,
            object : OnResultListener<PriceSize> {
                override fun onSuccess(list: PriceSize) {
                    view.onUpdatePriiceSizeSuccess(MESS_UPDATE_PRODUCT_SUCSESS)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_UPADTE_PRODUCT_FAIL)
                }

            }
        )
    }

    override fun checkInputUpdate(name: String, imageUri: String, price: String) {
        if(name.isNullOrEmpty() or imageUri.isNullOrEmpty() or price.isNullOrEmpty()){
            view.onFail(MESS_CHECK_INPUT_UPDATE_FAIL)
        }else{
            view.onCheckInputUpdateSuccess(imageUri)
        }
    }


    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        //TODO("Not yet implemented")
    }
    companion object{
        const val MESS_UPDATE_PRODUCT_SUCSESS = "cập nhật sản phẩm thành công"
        const val MESS_UPADTE_PRODUCT_FAIL = "cập nhật sản phẩm không thành công"
        const val MESS_CHECK_INPUT_UPDATE_FAIL = "Bạn cần nhập đầy đủ thông tin trước khi nhấn update"
    }

}