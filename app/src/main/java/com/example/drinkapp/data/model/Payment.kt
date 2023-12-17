package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment(
    var id: Long,
    var payment_type: Long,
    var payment_status: Long
):Parcelable
