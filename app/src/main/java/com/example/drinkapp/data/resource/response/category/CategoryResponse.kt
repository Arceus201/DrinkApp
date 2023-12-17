package com.example.drinkapp.data.resource.response.category


import android.os.Parcelable
import com.example.drinkapp.data.model.Category
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryResponse (
    val message: String,
    val categories :List<Category>
): Parcelable


