package com.example.drinkapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Address(
    var id: Long,
    var user: User,
    var name: String,
    var phone: String,
    var address: String
): Parcelable
