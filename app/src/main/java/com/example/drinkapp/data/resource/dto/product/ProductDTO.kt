package com.example.drinkapp.data.resource.dto.product

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductDTO (
    var name: String,
    var image: String,
    var price: Double,
    var status: Long,
    var category_id: Long
): Parcelable
