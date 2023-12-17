package com.example.drinkapp.utils

import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



fun Double.formatAsNumber(): String {
    try {
        val numberFormat = NumberFormat.getNumberInstance()
        if (numberFormat is DecimalFormat) {
            val pattern = "#,###,###"
            numberFormat.applyPattern(pattern)
        }
        return numberFormat.format(this)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return this.toString()
}


fun String.parseFromNumberFormat(): Double {
    try {
        val numberFormat = NumberFormat.getNumberInstance()
        if (numberFormat is DecimalFormat) {
            val pattern = "#,###,###"
            numberFormat.applyPattern(pattern)
        }

        return numberFormat.parse(this)?.toDouble() ?: 0.0
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0.0
}




fun String.convertToInternationalPhoneNumber(): String {
    if (this.startsWith("0")) {
        return "+84${this.substring(1)}"
    }
    return this
}

fun Double.format(amountInVnd: String): Double {
    val amountInVndWithoutCommas = amountInVnd.replace(",", "")
    val result = amountInVndWithoutCommas.toDouble() / this
    return String.format("%.2f", result).toDouble()
}

fun Long.toStatusString(): String {
    return when (this) {
        0L -> "Chờ xác nhận"
        1L -> "Shipper nhận đơn"
        2L -> "Đã xác nhận"
        3L -> "Đang giao"
        4L -> "Đã giao"
        else -> "Chờ xác nhận"
    }
}

fun Date.formatTimeCurrent(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(this)
}

fun Date.formatTimeStatistic(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(this)
}

fun EditText.setMaxLength(maxLength: Int) {
    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        val newLength = dest.length - (dend - dstart) + (end - start)
        if (newLength <= maxLength) {
            null // Chấp nhận dữ liệu nhập vào
        } else {
            // Nếu độ dài vượt quá giới hạn, loại bỏ các ký tự thêm vào
            val truncatedText = dest.subSequence(0, maxLength)
            setText(truncatedText)
            setSelection(maxLength) // Di chuyển con trỏ về cuối cùng
            error = "Độ dài không được vượt quá $maxLength ký tự."
            "" // Trả về một chuỗi rỗng để không chấp nhận dữ liệu nhập mới
        }
    }
    filters = arrayOf(filter)
}



