package com.example.drinkapp.data.resource.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLoginDTO (
    var phone: String = "",
    var password: String = ""
): Parcelable