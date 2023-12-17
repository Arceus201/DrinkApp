package com.example.drinkapp.screen.admin.category


import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiCategory

class CategoryPresenter(
    private val view: CategoryContract.View,
    private val callApi: CallApiCategory
) : CategoryContract.Presenter {


    override fun getAllCategory() {
        callApi.getAllCategory(
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    view.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view.displayFail()
                }
            }
        )
    }

    override fun addCategory(name: String) {
        callApi.addCategory(name,
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    view.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view.addUpDateFail(UPADTE_FAIL)
                }
            }
        )
    }

    override fun updateCategory(id: Long?, category: Category) {
        callApi.updateCategory(id,
            category,
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    view.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view.addUpDateFail(ADD_FAIL)
                }
            }
        )
    }

    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        // TODO("Not yet implemented")
    }

    companion object {
        const val UPADTE_FAIL = "cập nhật tên loại sản phẩm không thành công"
        const val ADD_FAIL = "thêm tên loại sản phẩm không thành công"
    }

}