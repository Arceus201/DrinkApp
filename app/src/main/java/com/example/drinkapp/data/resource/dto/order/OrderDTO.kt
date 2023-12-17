package com.example.drinkapp.data.resource.dto.order

import android.os.Parcelable
import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.resource.dto.payment.PaymentDTO
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class OrderDTO(
    var listCartItem: List<CartItem>,
    var address_id: Long,
    var paymentDTO: PaymentDTO,
    var order_time: String,
    var total_price: Double
): Parcelable