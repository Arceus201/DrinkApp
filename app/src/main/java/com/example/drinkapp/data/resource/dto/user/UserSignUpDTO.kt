package com.example.drinkapp.data.resource.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignUpDTO (
    var username: String,
    var phone: String ,
    var password: String ,
): Parcelable