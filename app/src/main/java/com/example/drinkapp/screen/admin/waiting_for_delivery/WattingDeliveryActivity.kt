package com.example.drinkapp.screen.admin.waiting_for_delivery

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.databinding.AdminActivityWaitingForDeliveryBinding
import com.example.drinkapp.screen.admin.adapter.RecylerViewOrderManagerAdapter
import com.example.drinkapp.screen.admin.delivering.DeliveringActivity
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemOrderShipperClickListener

class WattingDeliveryActivity :
    BaseActivity<AdminActivityWaitingForDeliveryBinding>(AdminActivityWaitingForDeliveryBinding::inflate),
    WattingDeliveryContract.View,
    OnItemOrderShipperClickListener {
    private val adapter = RecylerViewOrderManagerAdapter(this)
    private lateinit var presenter: WattingDeliveryPresenter
    private var listOrder: MutableList<Order> = mutableListOf()
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        presenter = WattingDeliveryPresenter(this, CallApiOrder.getInstance())
        presenter.getListOrder(2L)

    }

    override fun handleEvent() {
        binding.apply {
            buttonChoXacNhan.setOnClickListener {
                finish()
            }
            buttonDangGiao.setOnClickListener {
                val intent = Intent(this@WattingDeliveryActivity, DeliveringActivity::class.java)
                startActivity(intent)
                finish()
            }
            buttonSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val filteredList = filterOrderList(newText)
                    adapter.setData(filteredList)
                    return true
                }
            })
        }
    }

    private fun filterOrderList(query: String?): List<Order> {
        val filteredList = ArrayList<Order>()
        if (query.isNullOrBlank()) {
            filteredList.addAll(listOrder)
        } else {
            for (order in listOrder) {
                if (order.id.toString().contains(query, ignoreCase = true)) {
                    filteredList.add(order)
                }
            }
        }
        return filteredList
    }


    override fun onItemOrderShipperClick(id: Long) {
        val intent = Intent(this, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER, id)
        startActivity(intent)
    }

    override fun onItemReceiveOrderClick(id: Long) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(MESS_CONFIRM_WAITTING_FOR_DELIVERY_TILE)
        alertDialog.setMessage(MESS_CONFIRM_WAITTING_FOR_DELIVERY_MESSAGE)
        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            presenter.updateStatusOrder(id, 3L)
        }
        alertDialog.setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onGetListOrderSuccess(list: List<Order>) {
        listOrder = list as MutableList<Order>
        adapter.setData(list)
    }

    override fun onUpadteStatusOrderSuccess() {
        presenter.getListOrder(2L)
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onGetListOrderFail() {
        listOrder.clear()
        adapter.clearData()
    }

    companion object {
        const val MESS_CONFIRM_WAITTING_FOR_DELIVERY_TILE = "Xác nhận giao hàng"
        const val MESS_CONFIRM_WAITTING_FOR_DELIVERY_MESSAGE = "Bạn có chắc muốn xác nhận giao hàng?"

    }

}