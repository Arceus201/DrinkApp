package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RevenueStatistics(
    var name: String,
    var quantity: Long,
    var revenue: Double
):Parcelable
