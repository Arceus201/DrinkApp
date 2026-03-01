package com.example.drinkapp.screen.admin.drinkorders

import android.content.Intent
import android.widget.Toast
import com.example.drinkapp.R
import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.databinding.AdminActivityDrinkOrdersBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewDrinkOrderAdapter
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemDrinkOrderClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrinkOrderActivity:BaseActivity<AdminActivityDrinkOrdersBinding>(AdminActivityDrinkOrdersBinding::inflate),
DrinkOrderContract.View,
OnItemDrinkOrderClickListener{
    private val adapter =  RecyclerViewDrinkOrderAdapter(this)
    @Inject
    lateinit var presenterCoroutine: DrinkOrderPresenterCoroutine
    override fun initView() {
        binding.apply {
            // Configure CommonHeaderView
            commonHeader.configure {
                title = getString(R.string.text_drink_orders)
                showBackButton = true
                onBackClick = { finish() }
            }
            
            recyclerView.adapter = adapter
        }
    }

    override fun initData() {
        presenterCoroutine.attachView(this)
        val intent = getIntent()
        val name = intent.getStringExtra(Constant.KEY_NAME)
        val startTime = intent.getStringExtra(Constant.KEY_START)
        val endTime = intent.getStringExtra(Constant.KEY_END)
        if(name!=null && startTime!=null && endTime!=null){
            binding.apply {
                textStart.text = startTime
                textEnd.text = endTime
            }
            presenterCoroutine.getDrinkOrders(name,startTime + Constant.KEY_TIME_START,endTime + Constant.KEY_TIME_END)
        }

    }

    override fun handleEvent() {
        // No additional event handling needed - back button handled by CommonHeaderView
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

    override fun onDestroy() {
        presenterCoroutine.onStop()
        super.onDestroy()
    }
}