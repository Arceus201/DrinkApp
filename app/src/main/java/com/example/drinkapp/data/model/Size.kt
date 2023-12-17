package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Size (
    var id: Long,
    var name: String
): Parcelable, Serializable
