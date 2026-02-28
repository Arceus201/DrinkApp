package com.example.drinkapp.screen.admin.custom_manager_order

import com.example.drinkapp.data.repository.OrderRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based CustomManagerOrderPresenter with Hilt DI
 */
class CustomManagerOrderPresenterCoroutine @Inject constructor(
    private val repository: OrderRepository
) : CustomManagerOrderContract.Presenter, CoroutineScope {

    private var view: CustomManagerOrderContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: CustomManagerOrderContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getAllOrderByUserInUserManager(user_id: Long) {
        launch {
            when (val result = repository.getAllOrderByUserInUserManager(user_id)) {
                is Result.Success -> {
                    view?.onGetAllOrderByUserInUserManagerSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetAllOrderByUserInUserManagerFail()
                }
                is Result.HttpError -> {
                    view?.onGetAllOrderByUserInUserManagerFail()
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
