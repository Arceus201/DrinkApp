package com.example.drinkapp.screen.common.signup

import com.example.drinkapp.utils.base.BasePresenter
import javax.inject.Inject

class SignupPresenter @Inject constructor() : 
    BasePresenter<SignupContract.View>(), 
    SignupContract.Presenter {
    
    override fun checkValidPassword(password: String) {
        if(password.length >= 8 && containsLowerCase(password) && containsUpperCase(password)
            && containsSpecialCharacter(password) && containsNumber(password)){
            view?.isValidPasswordPass()
        }else{
            view?.isValidPassworFail()
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
}