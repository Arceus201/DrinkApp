package com.example.drinkapp.screen.common.confirmpassword

import android.content.Intent
import android.widget.Toast
import com.example.drinkapp.R
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.databinding.ClientActivityConfirmPasswordBinding
import com.example.drinkapp.screen.common.profile.ProfileActivity
import com.example.drinkapp.screen.common.resetpassword.ResetPasswordActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseActivity

class ConfirmPasswordActivity : BaseActivity<ClientActivityConfirmPasswordBinding>(ClientActivityConfirmPasswordBinding::inflate),
ConfirmPasswordContract.View{
    private lateinit var presenter: ConfirmPasswordPresenter
    override fun initView() {

    }

    override fun initData() {
        presenter = ConfirmPasswordPresenter(this, CallApiUser.getInstance())
    }

    override fun handleEvent() {

        binding.apply {
            buttonNext.setOnClickListener {
                val textConfirmPassword=textConfirmPassword.text.toString().trim()
                if(textConfirmPassword.isNullOrEmpty()){
                    onFail(getString(R.string.KEY_MISSING_INFORMATION))
                }else{
                    val user = UserManager.getUserInfo(this@ConfirmPasswordActivity)
                    user.id?.let { it1 -> presenter.conFirmPassword(it1,textConfirmPassword) }
                }
            }
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onConfirmPasswordSuccess() {
        val intent = intent.getStringExtra(Constant.KEY_PERSON)
        if(intent.equals(Constant.KEY_PERSON_PROFILE)){
            val intent = Intent(applicationContext,ProfileActivity::class.java)
            startActivity(intent)
        }else if(intent.equals(Constant.KEY_PERSON_RESET_PASSWORD)){
            val intent = Intent(applicationContext,ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        finish()
    }



    override fun onFail(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}