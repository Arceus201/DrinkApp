package com.example.drinkapp.screen.admin.category

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiCategory

class CategoryPresenter(
    private var view: CategoryContract.View?,
    private val callApi: CallApiCategory
) : CategoryContract.Presenter {

    override fun attachView(view: CategoryContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllCategory() {
        view?.showLoading()
        callApi.getAllCategory(
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    view?.hideLoading()
                    view?.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view?.hideLoading()
                    view?.displayFail()
                }
            }
        )
    }

    override fun addCategory(name: String) {
        view?.showLoading()
        callApi.addCategory(
            name,
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    view?.hideLoading()
                    view?.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view?.hideLoading()
                    view?.addUpDateFail(UPDATE_FAIL)
                }
            }
        )
    }

    override fun updateCategory(id: Long?, category: Category) {
        view?.showLoading()
        callApi.updateCategory(
            id,
            category,
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    view?.hideLoading()
                    view?.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view?.hideLoading()
                    view?.addUpDateFail(ADD_FAIL)
                }
            }
        )
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        detachView()
    }

    companion object {
        const val UPDATE_FAIL = "cập nhật tên loại sản phẩm không thành công"
        const val ADD_FAIL = "thêm tên loại sản phẩm không thành công"
    }
}