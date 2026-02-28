package com.example.drinkapp.screen.admin.custom_manager

import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.user.UserManagerDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based CustomManagerPresenter with Hilt DI
 */
class CustomManagerPresenterCoroutine @Inject constructor(
    private val repository: UserRepository
) : CustomManagerContract.Presenter, CoroutineScope {

    private var view: CustomManagerContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: CustomManagerContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllClient() {
        launch {
            when (val result = repository.getAllClient()) {
                is Result.Success -> {
                    view?.onGetAllClientSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetAllClientFail()
                }
                is Result.HttpError -> {
                    view?.onGetAllClientFail()
                }
            }
        }
    }

    override fun updateClientStatus(id: Long, role: Long) {
        val userManagerDTO = UserManagerDTO(id, role)
        launch {
            when (val result = repository.updateStatusClient(userManagerDTO)) {
                is Result.Success -> {
                    view?.onUpdateClientStatusSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(UPDATE_STATUS_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(UPDATE_STATUS_FAIL)
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
        const val UPDATE_STATUS_FAIL = "cập nhật trạng thái khách hàng không thành công"
    }
}
