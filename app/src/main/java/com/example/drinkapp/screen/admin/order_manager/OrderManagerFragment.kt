package com.example.drinkapp.screen.admin.order_manager

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.databinding.AdminFragmentOrderManagerBinding
import com.example.drinkapp.screen.admin.adapter.RecylerViewOrderManagerAdapter
import com.example.drinkapp.screen.admin.delivering.DeliveringActivity
import com.example.drinkapp.screen.admin.waiting_for_delivery.WattingDeliveryActivity
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseFragment
import com.example.drinkapp.utils.listener.OnItemOrderShipperClickListener

class OrderManagerFragment :
    BaseFragment<AdminFragmentOrderManagerBinding>(AdminFragmentOrderManagerBinding::inflate),
    OrderManagerContract.View,
    OnItemOrderShipperClickListener {
    private val adapter = RecylerViewOrderManagerAdapter(this)
    private lateinit var presenter: OrderManagerPresenter
    private val refreshHandler = Handler(Looper.getMainLooper())
    private val refreshInterval = 5000L
    private var listOrder: MutableList<Order> = mutableListOf()

    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        presenter = OrderManagerPresenter(this, CallApiOrder.getInstance())
        presenter.getListOrder(1L)
        scheduleRefresh()
    }
    private fun scheduleRefresh() {
        refreshHandler.postDelayed({
            presenter.getListOrder(1L)
            scheduleRefresh()
        }, refreshInterval)
    }


    override fun handleEvent() {
        binding.apply {
            buttonChoGiaoHang.setOnClickListener {
                val intent = Intent(context, WattingDeliveryActivity::class.java)
                startActivity(intent)
            }
            buttonDangGiao.setOnClickListener {
                val intent = Intent(context, DeliveringActivity::class.java)
                startActivity(intent)
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
        val intent = Intent(context, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER,id)
        startActivity(intent)
    }

    override fun onItemReceiveOrderClick(id: Long) {
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle(CONFIRM_ORDER_TILE)
        alertDialog.setMessage(CONFIRM_ORDER_MESSAGE)
        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            presenter.updateStatusOrder(id,2L)
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
        presenter.getListOrder(1L)
    }

    override fun onFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onGetListOrderFail() {
        listOrder.clear()
        adapter.clearData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        refreshHandler.removeCallbacksAndMessages(null)
    }
    companion object{
        const val CONFIRM_ORDER_TILE = "Xác nhận đơn hàng"
        const val CONFIRM_ORDER_MESSAGE = "Bạn có chắc muốn xác nhận đơn hàng?"
    }

}