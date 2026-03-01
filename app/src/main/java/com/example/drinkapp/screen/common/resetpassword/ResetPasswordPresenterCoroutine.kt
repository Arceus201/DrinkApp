package com.example.drinkapp.screen.common.resetpassword

import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.user.UserUpdatePasswordDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based ResetPasswordPresenter with Hilt DI
 */
class ResetPasswordPresenterCoroutine @Inject constructor(
    private val repository: UserRepository
) : ResetPasswordContract.Presenter, CoroutineScope {

    private var view: ResetPasswordContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: ResetPasswordContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun updatePassword(user_id: Long, current_password: String, new_password: String) {
        launch {
            when (val result = repository.updatePassword(user_id, current_password, new_password)) {
                is Result.Success -> {
                    view?.onUpdatePasswordSuccess()
                }
                is Result.Error -> {
                    view?.onFail(KEY_UPDATE_PASSWORD_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(KEY_UPDATE_PASSWORD_FAIL)
                }
            }
        }
    }

    override fun checkValidPassword(password: String) {
        if (password.length >= 8 && containsLowerCase(password) && containsUpperCase(password)
            && containsSpecialCharacter(password) && containsNumber(password)
        ) {
            view?.isValidPasswordPass()
        } else {
            view?.isValidPassworFail()
        }
    }

    private fun containsNumber(s: String): Boolean {
        return s.any { it.isDigit() }
    }

    private fun containsLowerCase(s: String): Boolean {
        return s != s.toUpperCase()
    }

    private fun containsUpperCase(s: String): Boolean {
        return s != s.toLowerCase()
    }

    private fun containsSpecialCharacter(s: String): Boolean {
        return s.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?].*".toRegex())
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }

    companion object {
        private const val KEY_UPDATE_PASSWORD_FAIL = "cập nhật mật khẩu không thành công"
    }
}
