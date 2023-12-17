package com.example.drinkapp.screen.common.login


import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser



class LoginPersenter(private val view: LoginContract.View, private val callApi : CallApiUser) :
    LoginContract.Presenter {
    override fun handleLogin(phone: String, password: String) {
        callApi.login(phone,password,
            object : OnResultListener<User>{
                override fun onSuccess(user: User) {
                    view.onLoginSuccess(user)
                }

                override fun onFail(message: String) {
                    view.onLoginFail(KEY_ERROR)
                }

            })
    }

    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        //TODO("Not yet implemented")
    }
    companion object{
        const val KEY_ERROR = "số điện thoại hoặc mật khẩu không chính xác"
    }
}