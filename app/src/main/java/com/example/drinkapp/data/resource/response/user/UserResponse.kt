package com.example.drinkapp.data.resource.response.user

import com.example.drinkapp.data.model.User

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponse(
    val message: String,
    val user: User
): Parcelable
