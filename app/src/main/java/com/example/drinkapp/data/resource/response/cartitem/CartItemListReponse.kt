package com.example.drinkapp.data.resource.response.cartitem

import android.os.Parcelable
import com.example.drinkapp.data.model.CartItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItemListReponse(
    val message: String,
    val cartitems: List<CartItem>
) : Parcelable