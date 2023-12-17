package com.example.drinkapp.screen.common.signup

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.utils.base.BaseActivity


class SignupPresenter(private val view: SignupContract.View): SignupContract.Presenter{
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

    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
       // TODO("Not yet implemented")
    }
}