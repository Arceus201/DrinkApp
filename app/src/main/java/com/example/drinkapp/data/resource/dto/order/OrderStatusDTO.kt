package com.example.drinkapp.data.resource.dto.order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderStatusDTO(
    var status: Long
): Parcelable