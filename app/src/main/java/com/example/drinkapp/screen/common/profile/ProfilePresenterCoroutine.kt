package com.example.drinkapp.screen.common.profile

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.user.UserUpdateDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based ProfilePresenter with Hilt DI
 */
class ProfilePresenterCoroutine @Inject constructor(
    private val repository: UserRepository
) : ProfileContract.Presenter, CoroutineScope {

    private var view: ProfileContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ProfileContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getUser(user_id: Long) {
        launch {
            when (val result = repository.getUserById(user_id)) {
                is Result.Success -> {
                    view?.onGetUserSuccess(result.data)
                }
                is Result.Error -> {
                    // Silent fail as per original implementation
                }
                is Result.HttpError -> {
                    // Silent fail as per original implementation
                }
            }
        }
    }

    override fun updateUser(user_id: Long, username: String, dob: String) {
        launch {
            val user = User(
                id = user_id,
                username = username,
                phone = "",  // Empty string as phone is required but not being updated
                password = "",  // Empty string as password is required but not being updated
                avatar = null,
                dob = dob,
                role = 0L
            )
            when (val result = repository.updateUser(user_id, user)) {
                is Result.Success -> {
                    view?.onUpdateUserSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESS_UPDATE_ACCOUNT_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_UPDATE_ACCOUNT_FAIL)
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
        const val MESS_UPDATE_ACCOUNT_FAIL = "cập nhật tài khoản không thành công"
    }
}
