package com.example.drinkapp.utils

import android.os.Handler
import android.os.Looper

object Constant {
    //INTENT
    const val KEY_PRODUCT = "product"
    const val KEY_PRODUCT_ID = "product_id"
    const val KEY_MISSING_INFORMATION = "bạn cần nhập đầy đủ các trường thông tin"
    const val CART_ITEM_LIST = "list_cart_item_chose"
    const val TOTAL_PRICE = "total_price"
    const val KEY_ORDER = "key_order"
    const val KEY_ADDRESS = "key_address"
    const val KEY_NAME = "name"
    const val KEY_START = "startTime"
    const val KEY_END = "endTime"
    const val KEY_ORDER_MANAGER = "order_manager"

    const val KEY_SIGNUP_NAME = "signup_name"
    const val KEY_SIGNUP_PHONE = "signup_phone"
    const val KEY_SIGNUP_PASSWORD = "signup_password"
    const val KEY_VERIFICATION_ID = "verificationId"

    const val KEY_PERSON = "person"
    const val KEY_PERSON_PROFILE = "person_profile"
    const val KEY_PERSON_RESET_PASSWORD = "person_reset_password"

    //TIME
    const val KEY_TIME_START = " 00:00:00"
    const val KEY_TIME_END = " 23:59:59"
    const val KEY_FORMAT_FULL_TIME = "yyyy_MM_dd_HH_mm_ss"

    //REPONSE
    const val MESS_CALL_API_FAIL = "call api fail"
    const val MESS_CALL_API_SUCCESS = "call api success"
    const val MESS_CALL_API_NOT_FOUND = "not found"

    //API GGMapAddress
    const val KEY_CONSTANT_KEY_QUERY_GG_MAP = "https://www.google.com/maps/search/?api=1&query="
    const val KEY_CONSTANT_KEY_CHECK_GG_MAP = "www.google.com/maps/search/?api=1&query="
    const val KEY_CONSTANT_KEY_ADDRESS_NAME = "ADDRESS_NAME"
    const val KEY_CONSTANT_KEY_VIET_NAM = "Việt Nam"


    val handler = Handler(Looper.getMainLooper())
}