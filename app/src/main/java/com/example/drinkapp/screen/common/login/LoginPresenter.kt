package com.example.drinkapp.screen.common.login

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val repository: UserRepository
) : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun handleLogin(phone: String, password: String) {
        launch {
            when (val result = repository.login(phone, password)) {
                is Result.Success -> {
                    val user = result.data
                    // Save user information using UserManager
                    // Note: UserManager.saveUserInfo requires Context, which should be passed from View
                    view?.onLoginSuccess(user)
                }
                is Result.HttpError -> {
                    if (result.code == 401) {
                        view?.onLoginFail("Tên đăng nhập hoặc mật khẩu không đúng")
                    } else {
                        view?.onLoginFail(result.message)
                    }
                }
                is Result.Error -> {
                    view?.onLoginFail(result.message)
                }
            }
        }
    }
}