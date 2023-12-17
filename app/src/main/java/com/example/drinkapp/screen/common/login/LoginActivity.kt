package com.example.drinkapp.screen.common.login

import android.content.Intent
import android.widget.Toast
import com.example.drinkapp.R
import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.databinding.ActivityLoginBinding
import com.example.drinkapp.screen.admin.main.MainAdminActivity
import com.example.drinkapp.screen.client.main.MainActivity
import com.example.drinkapp.screen.shipper.main.MainShipperActivity
import com.example.drinkapp.screen.common.signup.SignupActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.setMaxLength


class LoginActivity :
    BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    LoginContract.View {
    private lateinit var presenter: LoginPersenter
    private var isPasswordVisible1 = false

    override fun initView() {
        binding.apply {
            textPassword.setMaxLength(25)
            textPhone.setMaxLength(11)
        }
    }

    override fun initData() {
        presenter = LoginPersenter(this, CallApiUser.getInstance())
    }

    override fun handleEvent() {
        binding.apply {
            buttonSignIn.setOnClickListener {
                val phone = binding.textPhone.text.toString().trim()
                val password = binding.textPassword.text.toString().trim()
                if (!phone.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    presenter.handleLogin(phone, password)
                } else onLoginFail(Constant.KEY_MISSING_INFORMATION)
            }
            buttonSignUp.setOnClickListener {
                val intent = Intent(applicationContext, SignupActivity::class.java)
                startActivity(intent)
            }
            buttonShowHide1.setOnClickListener {
                isPasswordVisible1 = !isPasswordVisible1
                if (isPasswordVisible1) {
                    textPassword.inputType =
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    buttonShowHide1.setImageResource(R.drawable.ic_current_show)
                } else {
                    textPassword.inputType =
                        android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                    buttonShowHide1.setImageResource(R.drawable.ic_current_hide)
                }
            }
        }
    }

    override fun onLoginSuccess(user: User) {
        val id = user.id
        val role = user.role
        val name = user.username
        UserManager.saveUserInfo(id, role,name, applicationContext)
        val intent: Intent
        if (user?.role == -1L) onLoginFail(KEY_ACCOUNT_IS_BLOCKED)
        else {
            if (user?.role == 1L) {
                intent = Intent(this, MainActivity::class.java)
            } else if (user?.role == 2L) {
                intent = Intent(this, MainShipperActivity::class.java)
            } else {
                intent = Intent(this, MainAdminActivity::class.java)
            }
            startActivity(intent)
            finish()
        }

    }

    override fun onLoginFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY_ACCOUNT_IS_BLOCKED= "tài khoản của bạn đang bị chặn"
    }
}