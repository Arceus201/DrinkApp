package com.example.drinkapp.data.resource.response.size

import android.os.Parcelable
import com.example.drinkapp.data.model.Size
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SizeResponse (
    val message: String,
    val sizes :List<Size>
): Parcelable
