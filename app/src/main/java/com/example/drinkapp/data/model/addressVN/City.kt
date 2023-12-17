package com.example.drinkapp.data.model.addressVN

data class City(
    val name: String,
    val code: Int,
    val divisionType: String,
    val codename: String,
    val phoneCode: Int,
    val districts: List<District>
)

