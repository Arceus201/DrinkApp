package com.example.drinkapp.screen.admin.revenuestatistics

import com.example.drinkapp.data.model.RevenueStatistics
import java.util.*

interface RevenueStatisticsContract {
    interface View{
        fun onGetRevenueStatisticsSuccess(list: List<RevenueStatistics>)
        fun onGetRevenueStatisticsFail()
        fun onCheckTimeRevenueStatisticSuccess()
        fun onCheckTimeFail(msg: String)
        fun onFail(msg: String)
    }
    interface Presenter{
        fun getRevenueStatistics(startTime: String, endTime: String)
        fun checkTimeRevenueStatistic(startTime: Date, endTime: Date)
    }
}