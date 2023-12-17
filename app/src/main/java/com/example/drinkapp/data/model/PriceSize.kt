package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class PriceSize(
    var id: Long,
    var product: Product,
    var size: Size,
    var price: Double,
    var status: Long
) : Parcelable,Serializable