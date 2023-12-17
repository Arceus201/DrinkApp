package com.example.drinkapp.screen.admin.custom_manager

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.data.resource.dto.user.UserManagerDTO

class CustomManagerPresenter(private val view: CustomManagerContract.View,
private val callApi: CallApiUser): CustomManagerContract.Presenter {
    override fun getAllClient() {
        callApi.getAllClient(
            object : OnResultListener<List<User>>{
                override fun onSuccess(list: List<User>) {
                    view.onGetAllClientSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetAllClientFail()
                }

            }
        )
    }

    override fun updateClientStatus(id: Long, role: Long) {
        val userManagerDTO = UserManagerDTO(id,role);
        callApi.updateStatusClient(
            userManagerDTO,
            object : OnResultListener<List<User>>{
                override fun onSuccess(list: List<User>) {
                    view.onUpdateClientStatusSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(UPDATE_STATUS_FAIL)
                }

            }
        )
    }
    companion object{
        const val UPDATE_STATUS_FAIL = "cập nhật trạng thái khách hàng không thành công"
    }
}