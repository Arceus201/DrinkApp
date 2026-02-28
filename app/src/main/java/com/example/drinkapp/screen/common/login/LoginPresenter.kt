package com.example.drinkapp.screen.common.login

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser

class LoginPresenter(
    private var view: LoginContract.View?,
    private val callApi: CallApiUser
) : LoginContract.Presenter {

    override fun attachView(view: LoginContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun handleLogin(phone: String, password: String) {
        callApi.login(
            phone,
            password,
            object : OnResultListener<User> {
                override fun onSuccess(user: User) {
                    view?.onLoginSuccess(user)
                }

                override fun onFail(message: String) {
                    view?.onLoginFail(KEY_ERROR)
                }
            }
        )
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        detachView()
    }

    companion object {
        const val KEY_ERROR = "số điện thoại hoặc mật khẩu không chính xác"
    }
}