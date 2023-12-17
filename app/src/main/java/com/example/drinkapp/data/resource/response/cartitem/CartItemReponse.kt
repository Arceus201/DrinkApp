package com.example.drinkapp.data.resource.response.cartitem

import android.os.Parcelable
import com.example.drinkapp.data.model.CartItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItemReponse(
    val message: String,
    val cartitem: CartItem
) : Parcelable

