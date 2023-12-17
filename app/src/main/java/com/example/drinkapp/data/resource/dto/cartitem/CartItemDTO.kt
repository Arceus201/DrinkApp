package com.example.drinkapp.data.resource.dto.cartitem

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItemDTO(
    var pricesize_id: Long,
    var user_id: Long,
    var number: Long,
    var note: String
) : Parcelable
