package com.example.drinkapp.screen.common.launcher

import android.content.Intent
import android.util.Log
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.databinding.ActivityStartAppBinding
import com.example.drinkapp.screen.admin.main.MainAdminActivity
import com.example.drinkapp.screen.client.main.MainActivity
import com.example.drinkapp.screen.common.login.LoginActivity
import com.example.drinkapp.screen.shipper.main.MainShipperActivity
import com.example.drinkapp.utils.Constant.handler
import com.example.drinkapp.utils.TimeConstant.TIME_DELAY_START_APP
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseActivity

class LauncherActivity: BaseActivity<ActivityStartAppBinding>(ActivityStartAppBinding::inflate),
LauncherContract.View{
    private lateinit var presenter: LauncherPresenter
    override fun initView() {
        val userData = UserManager.getUserInfo(applicationContext)
        val id = userData.id
        val role = userData.role
        handler.postDelayed({
            Log.e("abc",role.toString())
                if(role == 0L){
                    val intent = Intent(this, MainAdminActivity::class.java)
                    startActivity(intent)
                    finish()
                }else if(role ==1L){
                    if (id != null) {
                        presenter.checkRoleClient(id)
                    }
                }else if (role == 2L){
                    val intent = Intent(this, MainShipperActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }, TIME_DELAY_START_APP)
    }

    override fun initData() {
       presenter = LauncherPresenter(this, CallApiUser.getInstance())
    }

    override fun handleEvent() {
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun activeAccount() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun accountIsBlocked() {
        UserManager.clearUserInfo(this)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}