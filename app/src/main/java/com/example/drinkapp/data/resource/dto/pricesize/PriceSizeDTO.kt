package com.example.drinkapp.data.resource.dto.pricesize

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceSizeDTO(
    var product_id: Long,
    var size_id: Long,
    var price: Double,
    var status: Long
) : Parcelable
