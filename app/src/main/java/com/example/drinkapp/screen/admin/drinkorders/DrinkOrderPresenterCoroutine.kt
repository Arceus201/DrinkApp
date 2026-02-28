package com.example.drinkapp.screen.admin.drinkorders

import com.example.drinkapp.data.repository.RevenueRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based DrinkOrderPresenter with Hilt DI
 */
class DrinkOrderPresenterCoroutine @Inject constructor(
    private val repository: RevenueRepository
) : DrinkOrderContract.Presenter, CoroutineScope {

    private var view: DrinkOrderContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: DrinkOrderContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getDrinkOrders(name: String, startTime: String, endTime: String) {
        launch {
            when (val result = repository.getAllDrinkOrders(name, startTime, endTime)) {
                is Result.Success -> {
                    view?.onGetDrinkOrdersSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetDrinkOrdersFail()
                }
                is Result.HttpError -> {
                    view?.onGetDrinkOrdersFail()
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
