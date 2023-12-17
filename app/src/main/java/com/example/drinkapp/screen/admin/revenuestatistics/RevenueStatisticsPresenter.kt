package com.example.drinkapp.screen.admin.revenuestatistics

import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiRevenue
import java.util.*

class RevenueStatisticsPresenter(
    private val view: RevenueStatisticsContract.View,
    private val callApi: CallApiRevenue
) : RevenueStatisticsContract.Presenter{
    override fun getRevenueStatistics(startTime: String, endTime: String) {
        callApi.getRevenueStatistics(
            startTime,endTime,
            object : OnResultListener<List<RevenueStatistics>>{
                override fun onSuccess(list: List<RevenueStatistics>) {
                    view.onGetRevenueStatisticsSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetRevenueStatisticsFail()
                }

            }
        )
    }

    override fun checkTimeRevenueStatistic(startTime: Date, endTime: Date) {
        val currentTime = Calendar.getInstance().time
        if (startTime!!.after(endTime)) {
            view.onCheckTimeFail(MESS_TIME_FAIL)
        } else if (startTime!!.after(currentTime)) {
            view.onCheckTimeFail(MESS_TIME_START_FAIL)
        } else {
            view.onCheckTimeRevenueStatisticSuccess()
        }
    }

    companion object{
        const val MESS_TIME_FAIL= "Thời gian bắt đầu không được lớn hơn thời gian kết thúc"
        const val MESS_TIME_START_FAIL = "Thời gian bắt đầu không được lớn hơn thời gian hiện tại"
    }
}