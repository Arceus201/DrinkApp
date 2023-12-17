package com.example.drinkapp.screen.common.person

import android.content.Intent
import android.view.View
import com.example.drinkapp.databinding.FragmentPersonBinding
import com.example.drinkapp.screen.common.address.AddressActivity
import com.example.drinkapp.screen.admin.main.MainAdminActivity
import com.example.drinkapp.screen.common.confirmpassword.ConfirmPasswordActivity
import com.example.drinkapp.screen.common.login.LoginActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseFragment

class PersonFragment:BaseFragment<FragmentPersonBinding>(FragmentPersonBinding::inflate) {
    override fun initView() {

    }

    override fun onResume() {
        super.onResume()
        val user = context?.let { UserManager.getUserInfo(it) }
        if(user!=null){
            val role = user.role
            if(role != 1L){
                binding.textAddress.visibility = View.INVISIBLE
                binding.buttonAddress.visibility = View.INVISIBLE
            }
            val name = user.name
            binding.textUsername.text = name
        }
    }

    override fun initData() {
        binding.apply {
            buttonProfile.setOnClickListener {
                val intent = Intent(context,ConfirmPasswordActivity::class.java)
                intent.putExtra(Constant.KEY_PERSON,Constant.KEY_PERSON_PROFILE)
                startActivity(intent)
            }
            buttonResetPassword.setOnClickListener {
                val intent = Intent(context,ConfirmPasswordActivity::class.java)
                intent.putExtra(Constant.KEY_PERSON,Constant.KEY_PERSON_RESET_PASSWORD)
                startActivity(intent)
            }
            buttonAddress.setOnClickListener {
                val intent = Intent(context, AddressActivity::class.java)
               startActivity(intent)
            }
        }
    }

    override fun handleEvent() {
        binding.apply {
            buttonLogOut.setOnClickListener{
                context?.let { it1 -> UserManager.clearUserInfo(it1) }
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

}