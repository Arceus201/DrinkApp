package com.example.drinkapp.screen.common.address

import com.example.drinkapp.data.model.Address

interface AddressContract {
    interface View{
        fun onGetAllAdressSuccess(list: List<Address>)
        fun onFail()
    }
    interface Presenter{
        fun getAllAddress(user_id: Long)
    }
}