package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Order(
        var id: Long,
        var listOrderItem: List<OrderItem>,
        var address: Address,
        var payment: Payment,
        var order_time: String,
        var confirm_order_time: String?=null,
        var order_status: Long,
        var total_price: Double,
        var shipping_price: Double,
        var shipper: User?=null
):Parcelable, Serializable