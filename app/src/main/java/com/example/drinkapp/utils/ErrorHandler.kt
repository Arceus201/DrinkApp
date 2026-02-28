package com.example.drinkapp.utils

import com.example.drinkapp.data.resource.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

object ErrorHandler {
    
    fun <T> getErrorMessage(response: Response<T>): String {
        return when (response.code()) {
            400 -> "Yêu cầu không hợp lệ"
            401 -> "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại"
            403 -> "Bạn không có quyền truy cập"
            404 -> "Không tìm thấy dữ liệu"
            408 -> "Hết thời gian chờ. Vui lòng thử lại"
            500 -> "Lỗi máy chủ. Vui lòng thử lại sau"
            502, 503 -> "Máy chủ đang bảo trì. Vui lòng thử lại sau"
            else -> parseErrorBody(response.errorBody()) ?: "Đã xảy ra lỗi. Vui lòng thử lại"
        }
    }
    
    fun parseErrorBody(errorBody: ResponseBody?): String? {
        return try {
            errorBody?.string()?.let { json ->
                val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
                errorResponse.message
            }
        } catch (e: Exception) {
            null
        }
    }
    
    fun getNetworkErrorMessage(throwable: Throwable): String {
        return when {
            throwable.message?.contains("timeout", ignoreCase = true) == true -> 
                "Hết thời gian chờ. Vui lòng kiểm tra kết nối mạng"
            throwable.message?.contains("unable to resolve host", ignoreCase = true) == true -> 
                "Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng"
            throwable.message?.contains("failed to connect", ignoreCase = true) == true -> 
                "Không thể kết nối. Vui lòng kiểm tra kết nối mạng"
            else -> throwable.message ?: "Lỗi mạng. Vui lòng thử lại"
        }
    }
}
