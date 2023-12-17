package com.example.drinkapp.data.resource.response.user

import android.os.Parcelable
import com.example.drinkapp.data.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListResponse(
    val message: String,
    val users: List<User>
): Parcelable
