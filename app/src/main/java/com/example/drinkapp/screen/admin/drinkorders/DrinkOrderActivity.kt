package com.example.drinkapp.screen.admin.drinkorders

import android.content.Intent
import android.widget.Toast
import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.data.resource.call.CallApiRevenue
import com.example.drinkapp.databinding.AdminActivityDrinkOrdersBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewDrinkOrderAdapter
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemDrinkOrderClickListener

class DrinkOrderActivity:BaseActivity<AdminActivityDrinkOrdersBinding>(AdminActivityDrinkOrdersBinding::inflate),
DrinkOrderContract.View,
OnItemDrinkOrderClickListener{
    private val adapter =  RecyclerViewDrinkOrderAdapter(this)
    private lateinit var presenter: DrinkOrderPresenter
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        presenter = DrinkOrderPresenter(this, CallApiRevenue.getInstance())
        val intent = getIntent()
        val name = intent.getStringExtra(Constant.KEY_NAME)
        val startTime = intent.getStringExtra(Constant.KEY_START)
        val endTime = intent.getStringExtra(Constant.KEY_END)
        if(name!=null && startTime!=null && endTime!=null){
            binding.apply {
                textStart.text = startTime
                textEnd.text = endTime
            }
            presenter.getDrinkOrders(name,startTime + Constant.KEY_TIME_START,endTime + Constant.KEY_TIME_END)
        }

    }

    override fun handleEvent() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onGetDrinkOrdersSuccess(list: List<DrinkOrders>) {
        adapter.setData(list)
    }

    override fun onGetDrinkOrdersFail() {
        adapter.clearData()
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(id_order: Long) {
        val intent = Intent(this, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER, id_order)
        startActivity(intent)
    }
}