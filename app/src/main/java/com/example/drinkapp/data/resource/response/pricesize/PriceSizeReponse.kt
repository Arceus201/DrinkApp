package com.example.drinkapp.data.resource.response.pricesize

import android.os.Parcelable
import com.example.drinkapp.data.model.PriceSize
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceSizeReponse(
    val message: String,
    val pricesize: PriceSize
) : Parcelable