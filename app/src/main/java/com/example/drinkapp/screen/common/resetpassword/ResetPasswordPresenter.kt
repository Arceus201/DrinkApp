package com.example.drinkapp.screen.common.resetpassword

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.data.resource.dto.user.UserUpdatePasswordDTO

class ResetPasswordPresenter(private val view: ResetPasswordContract.View,
private val callApi: CallApiUser): ResetPasswordContract.Presenter{
    override fun updatePassword(user_id: Long, current_password: String, new_password: String) {
        val userUpdatePasswordDTO = UserUpdatePasswordDTO(user_id,current_password,new_password)
        callApi.updatePassword(
            userUpdatePasswordDTO,
            object : OnResultListener<User>{
                override fun onSuccess(list: User) {
                    view.onUpdatePasswordSuccess()
                }

                override fun onFail(message: String) {
                    view.onFail(KEY_UPDATE_PASSWORD_FAIL)
                }

            }
        )
    }
    override fun checkValidPassword(password: String) {
        if(password.length >= 8 && containsLowerCase(password) && containsUpperCase(password)
            && containsSpecialCharacter(password) && containsNumber(password)){
            view.isValidPasswordPass()
        }else{
            view.isValidPassworFail()
        }
    }
    private fun containsNumber(s: String): Boolean {
        return s.any { it.isDigit() }
    }

    private fun containsLowerCase(s: String): Boolean {
        return s != s.toUpperCase()
    }

    private fun containsUpperCase(s: String): Boolean {
        return s != s.toLowerCase()
    }

    private fun containsSpecialCharacter(s: String): Boolean {
        return s.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?].*".toRegex())
    }

    companion object{
        private const val KEY_UPDATE_PASSWORD_FAIL = "cập nhật mật khẩu không thành công"
    }
}