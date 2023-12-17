package com.example.drinkapp.screen.common.addaddress

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.addressVN.City

interface AddAddressContract {
    interface View{

        fun onAddSuccess(address: Address)

        fun onGetAllAddressVnSuccess(list: List<City>)

        fun onGetAllAddressVnFail()
        fun onFail(msg: String)
    }
    interface Presenter{


        fun addAddress(user_id: Long,name:String,phone: String,address: String)

        fun getAllAddressVN()
    }
}