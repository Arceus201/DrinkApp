package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
data class Product (
    var id: Long,
    var name: String ,
    var image: String,
    var price: Double,
    var status: Long,
    var quantitysold: Long,
    var note: String? = null,
    var category: Category
): Parcelable, Serializable
