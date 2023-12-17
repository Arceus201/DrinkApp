package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderItem(
    var id:Long,
    var name: String,
    var image: String,
    var size: String,
    var price: Double,
    var quantity : Long,
    var note: String,
    var order_id: Long
):Parcelable
