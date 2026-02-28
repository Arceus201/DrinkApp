package com.example.drinkapp.screen.common.confirmpassword

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser

class ConfirmPasswordPresenter(private var view: ConfirmPasswordContract.View?,
private val callApi: CallApiUser): ConfirmPasswordContract.Presenter {
    
    override fun attachView(view: ConfirmPasswordContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        view = null
    }
    
    override fun conFirmPassword(user_id: Long, password: String) {
        callApi.checkPassword(
            user_id,
            password,
            object : OnResultListener<User>{
                override fun onSuccess(list: User) {
                    view?.onConfirmPasswordSuccess()
                }

                override fun onFail(message: String) {
                    view?.onFail(MESS_PASSWORD_INCORRECT)
                }

            }
        )
    }
    
    override fun onStart() {
    }

    override fun onStop() {
        detachView()
    }
    companion object{
        const val MESS_PASSWORD_INCORRECT = "mật khẩu không đúng"
    }

}