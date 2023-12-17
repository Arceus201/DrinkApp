package com.example.drinkapp.screen.common.addaddress


import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.addressVN.City
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiAddress
import com.example.drinkapp.data.resource.call.CallApiAddressVN
import com.example.drinkapp.data.resource.dto.address.AddressDTO

class AddAddressPresenter(
    private val view: AddAddressContract.View,
    private val callAPi: CallApiAddress,
    private val callApiAddressVn: CallApiAddressVN
) :
    AddAddressContract.Presenter {

    override fun addAddress(user_id: Long, name: String, phone: String, address: String) {
        val addressDTO = AddressDTO(user_id, name, phone, address)
        callAPi.addAddress(
            addressDTO,
            object : OnResultListener<Address> {
                override fun onSuccess(list: Address) {
                    view.onAddSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESSAGE_ADD_ADDRESS_FAIL)
                }

            }
        )
    }

    override fun getAllAddressVN() {
        callApiAddressVn.getAddressVN(
            object : OnResultListener<List<City>>{
                override fun onSuccess(list: List<City>) {
                    view.onGetAllAddressVnSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetAllAddressVnFail()
                }

            }
        )
    }
    companion object{
        const val MESSAGE_ADD_ADDRESS_FAIL = "thêm địa chỉ không thành công"
    }

}