package com.example.drinkapp.utils

object ApiConstant {
    //    Address
    const val API_KEY_ADD_ADDRESS = "/api/address/add"
    const val API_KEY_GET_ADDRESS = "/api/address/all/{user_id}"
    const val API_KEY_GET_STORE = "/api/address/store"

    //    Address VN
    const val API_KEY_GET_ADDRESS_VN = "/api/?depth=3"

    //    CartItem
    const val API_KEY_ADD_CARTITEM = "/api/cartitem/add"
    const val API_KEY_UPDTAE_CARTITEM_NUMBER = "/api/cartitem/update/{id}/{number}"
    const val API_KEY_UPDATE_CARTITEM = "/api/cartitem/update/{id}"
    const val API_KEY_GET_ALL_CARTITEM_BY_USER_ID = "/api/cartitem/all/{id}"
    const val API_KEY_CHECK_CARTITEM = "/api/cartitem/check/{user_id}/{pricesize_id}"
    const val API_KEY_DELETE_CARTITEM_BY_ID = "/api/cartitem/delete/{id}"
    const val API_KEY_DELETE_ALL_CARTITEM = "/api/cartitem/delete/all/{user_id}"

    //    CATEGORY
    const val API_KEY_CATEGORY_GET_ALL = "/api/categories/all"
    const val API_KEY_CATEGORY_ADD = "/api/categories/add"
    const val API_KE_CATEGORY_UPDATE = "/api/categories/{id}"

    //    Order
    const val API_KEY_ORDER_ADD = "/api/order/add"
    const val API_KEY_ORDER_UPDATE_SHIPPER = "/api/order/update/shipper/{id}"
    const val API_KEY_ORDER_UPDATE_STATUS = "/api/order/update/status/{id}"
    const val API_KEY_ORDER_GET = "/api/order/{id}"
    const val API_KEY_ORDER_GET_BY_USER_ID = "/api/order/all/{user_id}"
    const val API_KEY_ORDER_GET_HISTORY_BY_USER_ID = "/api/order/all/history/{user_id}"
    const val API_KEY_ORDER_GET_ALL_BY_USER_IN_USERMANAGER = "/api/order/all/usermanager/{user_id}"
    const val API_KEY_ORDER_GET_ALL_BY_STATUS = "/api/order/all/status/{order_status}"
    const val API_KEY_ORDER_GET_BY_SHIPPER_ID = "/api/order/shipper/{id}"

    //    PriceSize
    const val API_KEY_PRICESIZE_ADD = "/api/pricesize/add"
    const val API_KEY_PRICESIZE_UPDTAE = "/api/pricesize/update/{id}"
    const val API_KEY_PRICESIZE_UPDTAE_DEFAULT = "/api/pricesize/update/default"
    const val API_KEY_PRICESIZE_GET_ALL_BY_PRODUCT_ID = "/api/pricesize/all/{id}"
    const val API_KEY_PRICESIZE_GET_ALL_CLIENT_BY_PRODUCT_ID = "/api/pricesize/all/client/{id}"

    //    PRODUCT
    const val API_KEY_PRODUCT_ADD = "/api/products/add"
    const val API_KEY_PRODUCT_UPDATE = "api/products/update/{id}"
    const val API_KEY_PRODUCT_GET_ALL = "/api/products/all"
    const val API_KEY_PRODUCT_GET_HOT = "/api/products/hot"
    const val API_KEY_PRODUCT_GET_ALL_CLIENT = "/api/products/allP/client"
    const val API_KEY_PRODUCT_GET_BY_ID = "/api/products/{id}"
    const val API_KEY_PRODUCT_DELETE = "/api/products/delete/{id}"

    //    REVENUE
    const val API_KEY_REVENUE_GET = "/api/order/revenuestatistics"
    const val API_KEY_REVENUE_DRINK_ORDERS = "/api/order/drinkorders"

    //    SIZE
    const val API_KEY_SIZE_ADD = "/api/sizes/add"
    const val API_KEY_SIZE_UPDATE ="/api/sizes/{id}"
    const val API_KEY_SIZE_GET_ALL = "/api/sizes/all"

    //USER
    const val API_KEY_USER_REGISTER = "/api/users/register"
    const val API_KEY_USER_LOGIN ="/api/users/login"
    const val API_KEY_USER_UPDATE = "/api/users/update/profile"
    const val API_KEY_USER_UPDATE_PASSWORD ="/api/users/update/password"
    const val API_KEY_USER_UPDATE_STATUS = "/api/users/update/client/status"
    const val API_KEY_USER_CHECK_PASSWORD = "/api/users/check/password"
    const val API_KEY_USER_GET_BY_ID = "/api/users/{id}"
    const val API_KEY_USER_GET_ALL = "/api/users/all/client"


}