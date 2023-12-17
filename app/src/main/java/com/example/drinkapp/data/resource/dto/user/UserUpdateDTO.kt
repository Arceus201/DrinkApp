package com.example.drinkapp.data.resource.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUpdateDTO(
    var user_id: Long,
    var username: String,
    var dob: String?=null
):Parcelable
