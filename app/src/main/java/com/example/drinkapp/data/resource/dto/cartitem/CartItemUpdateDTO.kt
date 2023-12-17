package com.example.drinkapp.data.resource.dto.cartitem

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItemUpateDTO(
    var number: Long,
    var note: String,
) : Parcelable