package com.example.drinkapp.screen.common.signup

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.drinkapp.R
import com.example.drinkapp.databinding.ActivitySignupBinding
import com.example.drinkapp.screen.common.confirm_otp.ConfirmOTPActivity
import com.example.drinkapp.screen.common.login.LoginActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.convertToInternationalPhoneNumber
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class SignupActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate),
    SignupContract.View {
    private lateinit var presenter: SignupPresenter
    private lateinit var mAuth: FirebaseAuth
    private var isPasswordVisible1 = false
    private var isPasswordVisible2 = false
    private var checkPassword = false

    override fun initView() {

    }

    override fun initData() {
        mAuth = FirebaseAuth.getInstance()
        presenter = SignupPresenter(this)
    }

    override fun handleEvent() {
        binding.apply {
            buttonSignUp.setOnClickListener {
                val username = binding.textUsername.text.toString().trim()
                val phone = binding.textPhone.text.toString().trim()
                val password = binding.textPassword.text.toString().trim()
                val passwordConfirm = binding.textConfirmPassword.text.toString().trim()
                if (!username.isNullOrEmpty() && !phone.isNullOrEmpty()
                    && !passwordConfirm.isNullOrEmpty() && checkPassword
                ) {
                    if (password.equals(passwordConfirm)) {
                        onClickPhoneNumber(binding.textPhone.text.toString().trim())
                    } else onFail(KEY_CONFIRMPASSWORD_ERROR)

                } else {
                    onFail(Constant.KEY_MISSING_INFORMATION)
                }
            }
            textPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

                override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                    presenter.checkValidPassword(charSequence.toString())
                }
                override fun afterTextChanged(editable: Editable?) {}
            })

            textBackToLogin.setOnClickListener {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
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
            buttonShowHide2.setOnClickListener {
                isPasswordVisible2 = !isPasswordVisible2
                if (isPasswordVisible2) {
                    textConfirmPassword.inputType =
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    buttonShowHide2.setImageResource(R.drawable.ic_current_show)
                } else {
                    textConfirmPassword.inputType =
                        android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                    buttonShowHide2.setImageResource(R.drawable.ic_current_hide)
                }
            }
        }
    }

    fun onClickPhoneNumber(phone: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phone.convertToInternationalPhoneNumber())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onFail(KEY_VERIFICATION_FAILED)
                }

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, forceResendingToken)
                    gotoEnterOtpActivity(verificationId)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun gotoEnterOtpActivity(verificationId: String) {
        val intent = Intent(applicationContext, ConfirmOTPActivity::class.java)
        intent.putExtra(Constant.KEY_SIGNUP_NAME, binding.textUsername.text.toString().trim())
        intent.putExtra(Constant.KEY_SIGNUP_PHONE, binding.textPhone.text.toString().trim())
        intent.putExtra(Constant.KEY_SIGNUP_PASSWORD, binding.textPassword.text.toString().trim())
        intent.putExtra(Constant.KEY_VERIFICATION_ID, verificationId)
        startActivity(intent)
        finish()
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isValidPasswordPass() {
        checkPassword = true
        binding.textFormatPassword.visibility = View.GONE
    }

    override fun isValidPassworFail() {
        checkPassword = false
        binding.textFormatPassword.visibility = View.VISIBLE
    }

    companion object {
        const val KEY_CONFIRMPASSWORD_ERROR = "mật khẩu xác nhận không chính xác"
        const val KEY_VERIFICATION_FAILED = "Xác nhẫn lỗi"
    }
}

