package com.example.drinkapp.screen.admin.category

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.repository.CategoryRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based CategoryPresenter with Hilt DI
 * This is an example implementation showing how to migrate from callback-based to coroutine-based approach
 */
class CategoryPresenterCoroutine @Inject constructor(
    private val repository: CategoryRepository
) : CategoryContract.Presenter, CoroutineScope {

    private var view: CategoryContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: CategoryContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

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
                    view?.addUpDateFail(UPDATE_FAIL)
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.addUpDateFail(result.message)
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
                    view?.addUpDateFail(ADD_FAIL)
                }
                is Result.HttpError -> {
                    view?.hideLoading()
                    view?.addUpDateFail(result.message)
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
        const val UPDATE_FAIL = "cập nhật tên loại sản phẩm không thành công"
        const val ADD_FAIL = "thêm tên loại sản phẩm không thành công"
    }
}
