package com.example.drinkapp.screen.common.launcher

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser

class LauncherPresenter(
    private var view: LauncherContract.View?,
    private val callApi: CallApiUser
) :
    LauncherContract.Presenter {
    
    override fun attachView(view: LauncherContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        view = null
    }
    
    override fun checkRoleClient(id: Long) {
        callApi.getUserById(
            id,
            object : OnResultListener<User>{
                override fun onSuccess(list: User) {
                    if(list.role == 1L)  view?.activeAccount()
                    else view?.accountIsBlocked()
                }

                override fun onFail(message: String) {

                }

            }
        )
    }
    
    override fun onStart() {
    }

    override fun onStop() {
        detachView()
    }
}