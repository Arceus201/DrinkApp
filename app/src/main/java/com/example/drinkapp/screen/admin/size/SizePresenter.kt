package com.example.drinkapp.screen.admin.size

import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.repository.SizeRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SizePresenter @Inject constructor(
    private val repository: SizeRepository
) : BasePresenter<SizeContract.View>(), SizeContract.Presenter {

    override fun getAllSize() {
        launch {
            view?.showLoading()
            
            when (val result = repository.getAllSizes()) {
                is Result.Success -> {
                    view?.hideLoading()
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.hideLoading()
                    view?.displaySizeFail()
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.displaySizeFail()
                }
            }
        }
    }

    override fun addSize(name: String) {
        launch {
            view?.showLoading()
            
            when (val result = repository.addSize(name)) {
                is Result.Success -> {
                    view?.hideLoading()
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.hideLoading()
                    view?.displayFail(ADD_FAIL)
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.displayFail(ADD_FAIL)
                }
            }
        }
    }

    override fun updateSize(id: Long?, size: Size) {
        launch {
            view?.showLoading()
            
            when (val result = repository.updateSize(id, size)) {
                is Result.Success -> {
                    view?.hideLoading()
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.hideLoading()
                    view?.displayFail(UPDATE_FAIL)
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.displayFail(UPDATE_FAIL)
                }
            }
        }
    }

    companion object {
        const val UPDATE_FAIL = "cập nhật tên size không thành công"
        const val ADD_FAIL = "thêm tên size không thành công"
    }
}