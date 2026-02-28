package com.example.drinkapp.screen.common.address

import com.example.drinkapp.data.repository.AddressRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based AddressPresenter with Hilt DI
 */
class AddressPresenterCoroutine @Inject constructor(
    private val repository: AddressRepository
) : AddressContract.Presenter, CoroutineScope {

    private var view: AddressContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: AddressContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllAddress(user_id: Long) {
        launch {
            when (val result = repository.getAddress(user_id)) {
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

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }
}
