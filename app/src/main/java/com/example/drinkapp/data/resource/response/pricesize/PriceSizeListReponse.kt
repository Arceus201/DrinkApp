package com.example.drinkapp.data.resource.response.pricesize
import android.os.Parcelable
import com.example.drinkapp.data.model.PriceSize
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceSizeListReponse(
    val message: String,
    val pricesizes: List<PriceSize>
) : Parcelable