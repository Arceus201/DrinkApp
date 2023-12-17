package com.example.drinkapp.data.resource.response.drinkorders

import android.os.Message
import android.os.Parcelable
import com.example.drinkapp.data.model.DrinkOrders
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrinkOrdersReponse(
    var message: String,
    var drinkOrders : List<DrinkOrders>
) : Parcelable