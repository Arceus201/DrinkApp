package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Category (
    var id: Long,
    var name: String
): Parcelable, Serializable