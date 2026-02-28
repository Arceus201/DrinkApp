package com.example.drinkapp.screen.common.address

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.utils.base.BasePresenter

interface AddressContract {
    interface View{
        fun onGetAllAdressSuccess(list: List<Address>)
        fun onFail()
    }
    interface Presenter : BasePresenter<View> {
        fun getAllAddress(user_id: Long)
    }
}