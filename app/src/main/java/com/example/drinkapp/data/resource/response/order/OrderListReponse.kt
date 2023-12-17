package com.example.drinkapp.data.resource.response.order

import android.os.Parcelable
import com.example.drinkapp.data.model.Order
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderListReponse(
    var message: String,
    var order: List<Order>
): Parcelable