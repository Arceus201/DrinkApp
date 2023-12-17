package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrinkOrders(
    var name: String,
    var image: String,
    var orderId: Long,
    var orderTime: String
):Parcelable
