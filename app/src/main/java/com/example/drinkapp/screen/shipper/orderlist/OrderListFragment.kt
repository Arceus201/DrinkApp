package com.example.drinkapp.screen.shipper.orderlist

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.databinding.ShipperFragmentOrderlistBinding
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.screen.shipper.adapter.RecyclerViewOrderShipperAdapter
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseFragment
import com.example.drinkapp.utils.listener.OnItemOrderShipperClickListener
import com.example.drinkapp.utils.toStatusString

class OrderListFragment :
    BaseFragment<ShipperFragmentOrderlistBinding>(ShipperFragmentOrderlistBinding::inflate),
    OrderListContract.View,
    OnItemOrderShipperClickListener {
    private lateinit var presenter: OrderListPresenter
    private val adapter = RecyclerViewOrderShipperAdapter(this)
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        val userGet = context?.let { UserManager.getUserInfo(it) }
         presenter = OrderListPresenter(this, CallApiOrder.getInstance())
        if (userGet != null) {
            userGet.id?.let { presenter.getOrderByShipperId(it) }
        }
    }

    override fun handleEvent() {
        binding.apply {

        }
    }

    override fun onGetListOrderSuccess(list: List<Order>) {
        adapter.setData(list)
    }

    override fun onGetOrderByShipperIdSuccess(order: Order) {
        val userGet = context?.let { UserManager.getUserInfo(it) }
        binding.apply {
            recyclerView.visibility = View.INVISIBLE
            viewOrderShipper.visibility = View.VISIBLE
            textMa.setText(order.id.toString())
            textStatus.setText(order.order_status.toStatusString())
            if (order.order_status == 1L) {
                buttonDelivered.visibility = View.VISIBLE
                buttonDelivered.setText("HỦY ĐƠN")
            } else if(order.order_status == 2L) {
                buttonDelivered.visibility = View.GONE
            }else if(order.order_status == 3L){
                buttonDelivered.visibility = View.VISIBLE
                buttonDelivered.setText("ĐÃ GIAO")
            }
            buttonDelivered.setOnClickListener {
                if(buttonDelivered.text.equals("HỦY ĐƠN")){
                    if (userGet != null) {
                        val alertDialog = AlertDialog.Builder(it.context)
                        alertDialog.setTitle("Xác nhận hủy đơn")
                        alertDialog.setMessage("Bạn có chắc muốn hủy đơn?")
                        alertDialog.setPositiveButton("OK") { dialog, which ->
                            userGet.id?.let { presenter.updateShippingOrder(order.id, it,0L) }
                        }
                        alertDialog.setNegativeButton("Cancel") { dialog, which ->
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    }
                }else{
                    if (userGet != null) {
                        val alertDialog = AlertDialog.Builder(it.context)
                        alertDialog.setTitle("Xác nhận đã giao hàng")
                        alertDialog.setMessage("Bạn có chắc muốn xác nhận giao hàng?")
                        alertDialog.setPositiveButton("OK") { dialog, which ->
                            userGet.id?.let { presenter.updateShippingOrder(order.id, it,4L) }
                        }
                        alertDialog.setNegativeButton("Cancel") { dialog, which ->
                            dialog.dismiss()
                        }
                        alertDialog.show()

                    }
                }
            }
            viewOrderShipper.setOnClickListener {
                val intent = Intent(context, OrderDetailActivity::class.java)
                intent.putExtra(Constant.KEY_ORDER,order.id)
                startActivity(intent)
            }

        }
    }

    override fun updateShippingOrderSucess() {
        onResume()
    }

    override fun onFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onGetListOrderFail() {
        adapter.clearData()
    }

    override fun onGetOrderByShipperIdFail() {
        binding.apply {
            viewOrderShipper.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        }
        presenter.getListOrder(0L)
    }


    override fun onResume() {
        super.onResume()
        val userGet = context?.let { UserManager.getUserInfo(it) }
        presenter = OrderListPresenter(this, CallApiOrder.getInstance())
        if (userGet != null) {
            userGet.id?.let { presenter.getOrderByShipperId(it) }
        }
    }

    override fun onItemOrderShipperClick(id: Long) {
        val intent = Intent(context, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER,id)
        startActivity(intent)
    }

    override fun onItemReceiveOrderClick(id: Long) {
        val userGet = context?.let { UserManager.getUserInfo(it) }
        if (userGet != null) {
            Log.e("abc",id.toString())

            val alertDialog = AlertDialog.Builder(context!!)
            alertDialog.setTitle("Xác nhận nhận đơn")
            alertDialog.setMessage("Bạn có chắc muốn nhận đơn?")
            alertDialog.setPositiveButton("OK") { dialog, which ->
                userGet.id?.let { presenter.updateShippingOrder(id, it,1L) }
            }
            alertDialog.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()

        }
    }


}