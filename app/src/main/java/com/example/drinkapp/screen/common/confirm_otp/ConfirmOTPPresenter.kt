package com.example.drinkapp.screen.common.confirm_otp

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmOTPPresenter @Inject constructor(
    private val repository: UserRepository
) : BasePresenter<ConfirmOTPContract.View>(), ConfirmOTPContract.Presenter {

    override fun handlerSignUp(
        username: String,
        phone: String,
        password: String
    ) {
        launch {
            val user = User(
                id = 0L,
                username = username,
                phone = phone,
                password = password,
                avatar = null,
                dob = null,
                role = 0L
            )
            
            when (val result = repository.signUp(user)) {
                is Result.Success -> {
                    view?.onSignUpSuccess(MESSAGE_SIGN_UP_SUCCESS)
                }
                is Result.Error -> {
                    view?.onFail(result.message)
                }
                is Result.HttpError -> {
                    view?.onFail(result.message)
                }
            }
        }
    }

    companion object {
        const val MESSAGE_SIGN_UP_SUCCESS = "đăng ký tài khoản thành công"
        const val MESSAGE_SIGN_UP_FAIL = "đăng ký tài khoản không thành công"
    }
}