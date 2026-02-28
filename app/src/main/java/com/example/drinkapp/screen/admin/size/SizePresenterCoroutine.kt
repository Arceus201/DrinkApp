package com.example.drinkapp.screen.admin.size

import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.repository.SizeRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based SizePresenter with Hilt DI
 */
class SizePresenterCoroutine @Inject constructor(
    private val repository: SizeRepository
) : SizeContract.Presenter, CoroutineScope {

    private var view: SizeContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: SizeContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllSize() {
        launch {
            when (val result = repository.getAllSizes()) {
                is Result.Success -> {
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.displaySizeFail()
                }
                is Result.HttpError -> {
                    view?.displaySizeFail()
                }
            }
        }
    }

    override fun addSize(name: String) {
        launch {
            when (val result = repository.addSize(name)) {
                is Result.Success -> {
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.displayFail(ADD_FAIL)
                }
                is Result.HttpError -> {
                    view?.displayFail(result.message)
                }
            }
        }
    }

    override fun updateSize(id: Long?, size: Size) {
        launch {
            when (val result = repository.updateSize(id, size)) {
                is Result.Success -> {
                    view?.displaySuccess(result.data)
                }
                is Result.Error -> {
                    view?.displayFail(UPDATE_FAIL)
                }
                is Result.HttpError -> {
                    view?.displayFail(result.message)
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
        const val UPDATE_FAIL = "cập nhật tên size không thành công"
        const val ADD_FAIL = "thêm tên size không thành công"
    }
}
