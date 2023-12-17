package com.example.drinkapp.data.resource.dto.address

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class AddressDTO(
    var user_id: Long,
    var name: String,
    var phone: String,
    var address: String
): Parcelable
