package com.example.drinkapp.data.resource.dto.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLoginDTO (
    @SerializedName("phone")
    var phone: String = "",
    
    @SerializedName("password")
    var password: String = ""
): Parcelable