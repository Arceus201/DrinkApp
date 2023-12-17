package com.example.drinkapp.screen.client.order

import android.content.Intent
import android.widget.Toast
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.databinding.ClientFragmentOrderBinding
import com.example.drinkapp.screen.client.adapter.RecyclerViewOrderAdapter
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseFragment
import com.example.drinkapp.utils.listener.OnItemOrderClientClickListener

class OrderFragment:BaseFragment<ClientFragmentOrderBinding>(ClientFragmentOrderBinding::inflate),
    OrderContract.View,
    OnItemOrderClientClickListener{
    private lateinit var presenter: OrderPresenter
    private val adapter = RecyclerViewOrderAdapter(this)
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        val user = context?.let { UserManager.getUserInfo(it) }
        presenter = OrderPresenter(this, CallApiOrder.getInstance())
        if (user != null) {
            user.id?.let { presenter.getOrder(it) }
        }
    }

    override fun handleEvent() {
        //TODO("Not yet implemented")
    }

    override fun onGetAllOrderSuccess(list: List<Order>) {
        adapter.setData(list)
    }

    override fun onGetAllOrderFail() {
        adapter.clearData()
    }

    override fun onFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    override fun onItemOrderClientClick(id: Long) {
        val intent = Intent(context, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER, id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val user = context?.let { UserManager.getUserInfo(it) }
        presenter = OrderPresenter(this, CallApiOrder.getInstance())
        if (user != null) {
            user.id?.let { presenter.getOrder(it) }
        }
    }
}