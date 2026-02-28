package com.example.drinkapp.screen.admin.revenuestatistics

import com.example.drinkapp.data.repository.RevenueRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based RevenueStatisticsPresenter with Hilt DI
 */
class RevenueStatisticsPresenterCoroutine @Inject constructor(
    private val repository: RevenueRepository
) : RevenueStatisticsContract.Presenter, CoroutineScope {

    private var view: RevenueStatisticsContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: RevenueStatisticsContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getRevenueStatistics(startTime: String, endTime: String) {
        launch {
            when (val result = repository.getRevenueStatistics(startTime, endTime)) {
                is Result.Success -> {
                    view?.onGetRevenueStatisticsSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onGetRevenueStatisticsFail()
                }
                is Result.HttpError -> {
                    view?.onGetRevenueStatisticsFail()
                }
            }
        }
    }

    override fun checkTimeRevenueStatistic(startTime: Date, endTime: Date) {
        val currentTime = Calendar.getInstance().time
        if (startTime.after(endTime)) {
            view?.onCheckTimeFail(MESS_TIME_FAIL)
        } else if (startTime.after(currentTime)) {
            view?.onCheckTimeFail(MESS_TIME_START_FAIL)
        } else {
            view?.onCheckTimeRevenueStatisticSuccess()
        }
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }

    companion object {
        const val MESS_TIME_FAIL = "Thời gian bắt đầu không được lớn hơn thời gian kết thúc"
        const val MESS_TIME_START_FAIL = "Thời gian bắt đầu không được lớn hơn thời gian hiện tại"
    }
}
