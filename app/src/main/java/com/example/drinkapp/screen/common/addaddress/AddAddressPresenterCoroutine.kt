package com.example.drinkapp.screen.common.addaddress

import com.example.drinkapp.data.repository.AddressRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.address.AddressDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based AddAddressPresenter with Hilt DI
 */
class AddAddressPresenterCoroutine @Inject constructor(
    private val repository: AddressRepository
) : AddAddressContract.Presenter, CoroutineScope {

    private var view: AddAddressContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: AddAddressContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

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
        // Note: This method requires a separate AddressVN API/Repository
        // The original implementation uses CallApiAddressVN which is a separate dependency
        // This should be refactored to inject the appropriate repository
        // For now, leaving this as a placeholder to maintain interface compatibility
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }

    companion object {
        const val MESSAGE_ADD_ADDRESS_FAIL = "thêm địa chỉ không thành công"
    }
}
