package com.example.drinkapp.screen.common.confirm_otp

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser

class ConfirmOTPPresenter (private val view: ConfirmOTPContract.View, private val callApi: CallApiUser) :
    ConfirmOTPContract.Presenter {

    override fun handlerSignUp(
        username: String,
        phone: String,
        password: String
    ) {
        callApi.signUp(
            username,
            phone,
            password,
            object : OnResultListener<User> {
                override fun onSuccess(user: User) {
                    view.onSignUpSuccess(MESSAGE_SIGN_UP_SUCCESS)
                }

                override fun onFail(message: String) {
                    view.onFail(MESSAGE_SIGN_UP_FAIL)
                }

            }
        )
    }


    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        //TODO("Not yet implemented")
    }
    companion object{
        const val MESSAGE_SIGN_UP_SUCCESS = "đăng ký tài khoản thành công"
        const val MESSAGE_SIGN_UP_FAIL = "đăng ký tài khoản không thành công"
    }
}