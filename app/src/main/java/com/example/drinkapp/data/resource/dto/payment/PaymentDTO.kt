package com.example.drinkapp.data.resource.dto.payment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentDTO(
    var payment_type: Long,
    var payment_status: Long
): Parcelable