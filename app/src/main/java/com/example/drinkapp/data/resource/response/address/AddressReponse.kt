package com.example.drinkapp.data.resource.response.address

import android.os.Parcelable
import com.example.drinkapp.data.model.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressReponse(
    var message: String,
    var address: Address
):Parcelable
