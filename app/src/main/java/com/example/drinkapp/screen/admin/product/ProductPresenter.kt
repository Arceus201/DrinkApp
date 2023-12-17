package com.example.drinkapp.screen.admin.product

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiDrink

class ProductPresenter(private val view: ProductContract.View, private val callApi: CallApiDrink) :
    ProductContract.Presenter {
    override fun getAllDrink() {
        callApi.getAllDrink(
            object : OnResultListener<List<Product>>{
                override fun onSuccess(list: List<Product>) {
                    view.showAllDrinkSuccess(list)
                }

                override fun onFail(message: String) {
                    view.showAllDrinkFail()
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
                    view.onUpdateSuccess(MESS_UPDATE_STATUS_SUCCESS)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_UPDATE_STATUS_FAIL)
                }
            })
    }

    override fun deleteProduct(id: Long) {
        callApi.deleteProductById(
            id,
            object : OnResultListener<String>{
                override fun onSuccess(list: String) {
                   view.onDeleteProductSuccess(MESS_DELETE_SUCCESS)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_DELETE_FAIL)
                }

            }
        )
    }




    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        //TODO("Not yet implemented")
    }

    companion object{
        const val MESS_UPDATE_STATUS_SUCCESS = "cập nhật trạng thái sản phẩm thành công"
        const val MESS_UPDATE_STATUS_FAIL = "cập nhật trạng thái sản phẩm không thành công"
        const val MESS_DELETE_FAIL = "xóa sản phẩm không thành công"
        const val MESS_DELETE_SUCCESS = "xóa sản phẩm thành công"
    }
}