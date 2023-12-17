package com.example.drinkapp.data.resource.response.product

import android.os.Parcelable
import com.example.drinkapp.data.model.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductListResponse(
    val message: String,
    val products: List<Product>
) : Parcelable
