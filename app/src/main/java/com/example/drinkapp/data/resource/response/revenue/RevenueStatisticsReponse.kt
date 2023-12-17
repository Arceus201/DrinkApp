package com.example.drinkapp.data.resource.response.revenue

import android.os.Parcelable
import com.example.drinkapp.data.model.RevenueStatistics
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RevenueStatisticsReponse (
    val message: String,
    val revenue: List<RevenueStatistics>
):Parcelable