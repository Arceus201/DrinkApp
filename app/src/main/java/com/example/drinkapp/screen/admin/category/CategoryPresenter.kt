package com.example.drinkapp.screen.admin.category

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.repository.CategoryRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryPresenter @Inject constructor(
    private val repository: CategoryRepository
) : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    override fun getAllCategory() {
        launch {
            view?.showLoading()
            
            when (val result = repository.getAllCategories()) {
                is Result.Success -> {
                    view?.hideLoading()
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.hideLoading()
                    view?.displayFail()
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.displayFail()
                }
            }
        }
    }

    override fun addCategory(name: String) {
        launch {
            view?.showLoading()
            
            when (val result = repository.addCategory(name)) {
                is Result.Success -> {
                    view?.hideLoading()
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.hideLoading()
                    view?.addUpDateFail(ADD_FAIL)
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.addUpDateFail(ADD_FAIL)
                }
            }
        }
    }

    override fun updateCategory(id: Long?, category: Category) {
        launch {
            view?.showLoading()
            
            when (val result = repository.updateCategory(id, category)) {
                is Result.Success -> {
                    view?.hideLoading()
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.hideLoading()
                    view?.addUpDateFail(UPDATE_FAIL)
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.addUpDateFail(UPDATE_FAIL)
                }
            }
        }
    }

    companion object {
        const val UPDATE_FAIL = "cập nhật tên loại sản phẩm không thành công"
        const val ADD_FAIL = "thêm tên loại sản phẩm không thành công"
    }
}