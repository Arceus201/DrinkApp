package com.example.drinkapp.screen.common.addaddress

import com.example.drinkapp.data.repository.AddressRepository
import com.example.drinkapp.data.repository.AddressVNRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.address.AddressDTO
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Coroutine-based AddAddressPresenter with Hilt DI
 */
class AddAddressPresenterCoroutine @Inject constructor(
    private val repository: AddressRepository,
    private val addressVNRepository: AddressVNRepository
) : BasePresenter<AddAddressContract.View>(), AddAddressContract.Presenter {

    override fun addAddress(user_id: Long, name: String, phone: String, address: String) {
        val addressDTO = AddressDTO(user_id, name, phone, address)
        launch {
            when (val result = repository.addAddress(addressDTO)) {
                is Result.Success -> {
                    view?.onAddSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESSAGE_ADD_ADDRESS_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESSAGE_ADD_ADDRESS_FAIL)
                }
            }
        }
    }

    override fun getAllAddressVN() {
        launch {
            when (val result = addressVNRepository.getProvinces()) {
                is Result.Success -> {
                    view?.onGetAllAddressVnSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetAllAddressVnFail()
                }
                is Result.HttpError -> {
                    view?.onGetAllAddressVnFail()
                }
            }
        }
    }

    companion object {
        const val MESSAGE_ADD_ADDRESS_FAIL = "thêm địa chỉ không thành công"
    }
}
