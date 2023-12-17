package com.example.drinkapp.screen.common.address

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiAddress

class AddressPresenter (private val view: AddressContract.View,
                        private val callAPi: CallApiAddress): AddressContract.Presenter {
    override fun getAllAddress(user_id: Long) {
        callAPi.getAddress(
            user_id,
            object : OnResultListener<List<Address>>{
                override fun onSuccess(list: List<Address>) {
                    view.onGetAllAdressSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail()
                }
            }
        )
    }
}