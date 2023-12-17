package com.example.drinkapp.screen.common.confirm_otp

import android.content.Intent
import android.os.CountDownTimer
import android.widget.Toast
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.databinding.ActivityConfirmOtpCodeBinding
import com.example.drinkapp.screen.common.login.LoginActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.convertToInternationalPhoneNumber
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class ConfirmOTPActivity :
    BaseActivity<ActivityConfirmOtpCodeBinding>(ActivityConfirmOtpCodeBinding::inflate),
    ConfirmOTPContract.View {
    private lateinit var presenter: ConfirmOTPPresenter
    private lateinit var mAuth: FirebaseAuth
    private var username: String? = null
    private var phone: String? = null
    private var password: String? = null
    private var mVerificationId: String? = null
    private var mForceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private val totalTimeInMillis: Long = 60000
    private lateinit var countDownTimer: CountDownTimer


    override fun initView() {
        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.textCountdown.text = "$secondsRemaining s"
            }

            override fun onFinish() {
                binding.textCountdown.text = MESS_OTP_IS_EXPIRED
            }
        }
        countDownTimer.start()
    }

    override fun initData() {
        getDataIntent()
        mAuth = FirebaseAuth.getInstance()
        presenter = ConfirmOTPPresenter(this, CallApiUser.getInstance())
    }

    override fun handleEvent() {
        binding.apply {
            buttonConfirmOtp.setOnClickListener {
                onConfirmOTPCode(textOtpCode.text.toString().trim())
            }
//            buttonSendOtpAgain.setOnClickListener {
//                onClickSendOtpAgain()
//            }
            buttonBack.setOnClickListener {
                countDownTimer.cancel()
                finish()
            }
        }
    }

    fun getDataIntent() {
        val intent = getIntent()
        if (intent != null) {
            username = intent.getStringExtra(Constant.KEY_SIGNUP_NAME)
            phone = intent.getStringExtra(Constant.KEY_SIGNUP_PHONE)
            password = intent.getStringExtra(Constant.KEY_SIGNUP_PASSWORD)
            mVerificationId = intent.getStringExtra(Constant.KEY_VERIFICATION_ID)
        }
    }

    fun onClickSendOtpAgain() {
        val options = phone?.let {
            mForceResendingToken?.let { it1 ->
                PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber(it.convertToInternationalPhoneNumber())
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setForceResendingToken(it1)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                            signInWithPhoneAuthCredential(phoneAuthCredential)
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            onFail(MESS_VERIFICATION_FAIL)
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            forceResendingToken: PhoneAuthProvider.ForceResendingToken
                        ) {
                            super.onCodeSent(verificationId, forceResendingToken)
                            mVerificationId = verificationId
                            mForceResendingToken = forceResendingToken
                        }
                    })
                    .build()
            }
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    fun onConfirmOTPCode(otpCode: String) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, otpCode)
        signInWithPhoneAuthCredential(credential)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    user?.phoneNumber?.let {
                        presenter.handlerSignUp(username!!, phone!!, password!!)
                    }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        onFail(MESS_THE_VERIFICATION_INVALID)
                    }
                }
            })
    }


    override fun onSignUpSuccess(msg: String) {
        countDownTimer.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
    companion object{
        const val MESS_VERIFICATION_FAIL = "Xác minh không thành công"
        const val MESS_THE_VERIFICATION_INVALID = "Mã xác minh đã nhập không hợp lệ"
        const val MESS_OTP_IS_EXPIRED = "mã OTP hết hiệu lực"
    }

}