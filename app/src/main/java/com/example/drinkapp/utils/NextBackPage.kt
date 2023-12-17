package com.example.drinkapp.utils

import android.content.Context
import android.content.Intent

class NextBackPage (private val context: Context) {
    fun startActivity(targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
    }
}