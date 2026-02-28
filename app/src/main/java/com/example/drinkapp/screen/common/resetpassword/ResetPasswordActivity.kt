package com.example.drinkapp.screen.common.resetpassword


import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.drinkapp.R
import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.databinding.ClientActivityResetPasswordBinding
import com.example.drinkapp.screen.common.login.LoginActivity
import com.example.drinkapp.screen.common.signup.SignupActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.setMaxLength

class ResetPasswordActivity :
    BaseActivity<ClientActivityResetPasswordBinding>(ClientActivityResetPasswordBinding::inflate),
    ResetPasswordContract.View {
    private lateinit var presenter: ResetPasswordPresenter
    private var checkPassword = false
    override fun initView() {
        binding.apply {
            // Setup toolbar
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener { finish() }
            
            textRecentPassword.setMaxLength(25)
            textNewPassword.setMaxLength(25)
            textConfirmpassword.setMaxLength(25)
        }
    }

    override fun initData() {
        presenter = ResetPasswordPresenter(null, CallApiUser.getInstance())
        presenter.attachView(this)
    }

    override fun handleEvent() {

        binding.apply {
            buttonUpdate.setOnClickListener {
                val user = UserManager.getUserInfo(this@ResetPasswordActivity)
                val textRecentPassword = textRecentPassword.text.toString().trim()
                val textNewPassword = textNewPassword.text.toString().trim()
                val textConfirmPassword = textConfirmpassword.text.toString().trim()
                if(!textRecentPassword.isNullOrEmpty() && !textNewPassword.isNullOrEmpty()
                    && !textConfirmPassword.isNullOrEmpty() && checkPassword){
                    if (textNewPassword.equals(textConfirmPassword)) {
                        user.id?.let { it1 ->
                            presenter.updatePassword(
                                it1,
                                textRecentPassword,
                                textNewPassword
                            )
                        }
                    } else onFail(KEY_CONFIRMPASSWORD_ERROR)

                }else {
                    onFail(Constant.KEY_MISSING_INFORMATION)
                }

            }
            textNewPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

                override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                    presenter.checkValidPassword(charSequence.toString())
                }
                override fun afterTextChanged(editable: Editable?) {}
            })
        }
    }

    override fun onUpdatePasswordSuccess() {
        onFail(KEY_UPDATE_PASSWORD_SUCCESS)
        UserManager.clearUserInfo(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun isValidPasswordPass() {
        checkPassword = true
        binding.textFormat.visibility = View.GONE
    }

    override fun isValidPassworFail() {
        checkPassword = false
        binding.textFormat.visibility = View.VISIBLE
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onStop()
        super.onDestroy()
    }

    companion object{
        const val KEY_CONFIRMPASSWORD_ERROR = "mật khẩu xác nhận không chính xác"
        private const val KEY_UPDATE_PASSWORD_SUCCESS = "cập nhật mật khẩu thành công"
    }
}