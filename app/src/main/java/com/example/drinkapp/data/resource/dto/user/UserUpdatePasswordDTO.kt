package com.example.drinkapp.data.resource.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUpdatePasswordDTO(
    var user_id: Long,
    var current_password: String,
    var new_password: String
):Parcelable
