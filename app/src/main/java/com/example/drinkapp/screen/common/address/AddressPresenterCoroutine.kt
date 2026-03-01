package com.example.drinkapp.screen.common.address

import com.example.drinkapp.data.repository.AddressRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressPresenterCoroutine @Inject constructor(
    private val repository: AddressRepository
) : BasePresenter<AddressContract.View>(), AddressContract.Presenter {

    override fun getAllAddress(user_id: Long) {
        launch {
            when (val result = repository.getAddressesByUserId(user_id)) {
                is Result.Success -> {
                    view?.onGetAllAdressSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail()
                }
                is Result.HttpError -> {
                    view?.onFail()
                }
            }
        }
    }
}
