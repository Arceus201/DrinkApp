package com.example.drinkapp.data.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import java.io.Serializable
import java.net.Inet4Address

@Parcelize
data class User (
    var id: Long,
    var username: String ,
    var password: String ,
    var phone: String ,
    var avatar: String?=null ,
    var dob: String?=null ,
    var role: Long
):  Parcelable,Serializable
