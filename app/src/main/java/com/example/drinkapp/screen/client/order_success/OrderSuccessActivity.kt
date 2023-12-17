package com.example.drinkapp.screen.client.order_success


import android.content.Intent
import com.example.drinkapp.databinding.ClientActivityOrderSuccessBinding
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant

import com.example.drinkapp.utils.base.BaseActivity

class OrderSuccessActivity: BaseActivity<ClientActivityOrderSuccessBinding>(ClientActivityOrderSuccessBinding::inflate) {
    override fun initView() {
        //TODO("Not yet implemented")
    }

    override fun initData() {
        //TODO("Not yet implemented")
    }

    override fun handleEvent() {
        val order_information = intent.getSerializableExtra(Constant.KEY_ORDER) as Long
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
            buttonStartOrderDetail.setOnClickListener {
                val intent = Intent(applicationContext,OrderDetailActivity::class.java)
                intent.putExtra(Constant.KEY_ORDER,order_information)
                startActivity(intent)
                finish()
            }
        }
    }
}