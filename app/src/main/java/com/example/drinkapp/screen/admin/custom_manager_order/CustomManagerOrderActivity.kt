package com.example.drinkapp.screen.admin.custom_manager_order

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.databinding.AdminActivityCustomManagerOrderBinding
import com.example.drinkapp.screen.client.adapter.RecyclerViewOrderAdapter
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemOrderClientClickListener

class CustomManagerOrderActivity :
    BaseActivity<AdminActivityCustomManagerOrderBinding>(AdminActivityCustomManagerOrderBinding::inflate)
    ,
    CustomManagerOrderContract.View,
    OnItemOrderClientClickListener
{
    private lateinit var presenter: CustomManagerOrderPresenter
    private val adapter: RecyclerViewOrderAdapter = RecyclerViewOrderAdapter(this)
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        var user_id = intent.getLongExtra(Constant.KEY_ORDER_MANAGER,-1L)
        presenter = CustomManagerOrderPresenter(this, CallApiOrder.getInstance())
        if(user_id != -1L) presenter.getAllOrderByUserInUserManager(user_id)
    }

    override fun handleEvent() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onItemOrderClientClick(id: Long) {
        val intent = Intent(applicationContext, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER,id)
        startActivity(intent)
    }

    override fun onGetAllOrderByUserInUserManagerSuccess(list: List<Order>) {
        adapter.setData(list)
    }

    override fun onGetAllOrderByUserInUserManagerFail() {
        adapter.clearData()
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}