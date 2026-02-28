package com.example.drinkapp.screen.common.confirmpassword

import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based ConfirmPasswordPresenter with Hilt DI
 */
class ConfirmPasswordPresenterCoroutine @Inject constructor(
    private val repository: UserRepository
) : ConfirmPasswordContract.Presenter, CoroutineScope {

    private var view: ConfirmPasswordContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ConfirmPasswordContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun conFirmPassword(user_id: Long, password: String) {
        launch {
            when (val result = repository.checkPassword(user_id, password)) {
                is Result.Success -> {
                    view?.onConfirmPasswordSuccess()
                }
                is Result.Error -> {
                    view?.onFail(MESS_PASSWORD_INCORRECT)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_PASSWORD_INCORRECT)
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
        const val MESS_PASSWORD_INCORRECT = "mật khẩu không đúng"
    }
}
