package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class CartItem(
    var id: Long,
    var priceSize: PriceSize,
    var user: User,
    var number: Long,
    var note: String,
) : Parcelable, Serializable