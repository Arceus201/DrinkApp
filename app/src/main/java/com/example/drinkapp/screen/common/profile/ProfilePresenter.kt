package com.example.drinkapp.screen.common.profile

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.data.resource.dto.user.UserUpdateDTO

class ProfilePresenter(
    private val view: ProfileContract.View,
    private val callApi: CallApiUser
) : ProfileContract.Presenter {
    override fun getUser(user_id: Long) {
        callApi.getUserById(
            user_id,
            object : OnResultListener<User>{
                override fun onSuccess(list: User) {
                    view.onGetUserSuccess(list)
                }

                override fun onFail(message: String) {

                }

            }
        )
    }

    override fun updateUser(user_id:Long,username: String, dob: String) {
        val userUpdateDTO = UserUpdateDTO(user_id,username,dob)
        callApi.updateUser(
            userUpdateDTO,
            object : OnResultListener<User>{
                override fun onSuccess(list: User) {
                    view.onUpdateUserSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_UPDATE_ACCOUNT_FAIL)
                }

            }
        )
    }
    companion object{
        const val MESS_UPDATE_ACCOUNT_FAIL = "cập nhật tài khoản không thành công"
    }
}