package com.example.drinkapp.data.resource.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserManagerDTO(
    private var id: Long,
    private var role: Long
):Parcelable
