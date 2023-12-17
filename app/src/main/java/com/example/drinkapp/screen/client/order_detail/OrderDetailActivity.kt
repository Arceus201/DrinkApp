package com.example.drinkapp.screen.client.order_detail

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.drinkapp.R
import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.call.CallApiAddress
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.databinding.ClientActivityOrderDetailBinding
import com.example.drinkapp.screen.client.adapter.RecyclerViewOrderDetailAdapter
import com.example.drinkapp.screen.client.ggmap.GgMapActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.formatAsNumber

class OrderDetailActivity: BaseActivity<ClientActivityOrderDetailBinding>(ClientActivityOrderDetailBinding::inflate),
    OrderDetailContract.View{
    private lateinit var presenter: OrderDetailPresenter
    private val adapter: RecyclerViewOrderDetailAdapter = RecyclerViewOrderDetailAdapter()
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        val order_information = intent.getSerializableExtra(Constant.KEY_ORDER) as Long
        presenter = OrderDetailPresenter(
            this,
            CallApiOrder.getInstance(),
            CallApiAddress.getInstance()
        )
        presenter.getOrder(order_information)
        presenter.getAddressStore()
    }

    override fun handleEvent() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
            buttonAddressStore.setOnClickListener {
                val intent = Intent(applicationContext, GgMapActivity::class.java)
                intent.putExtra(Constant.KEY_CONSTANT_KEY_ADDRESS_NAME, textAddressAdmin.text.toString().trim())
                startActivity(intent)
            }
            buttonAddressClient.setOnClickListener {
                val intent = Intent(applicationContext, GgMapActivity::class.java)
                intent.putExtra(Constant.KEY_CONSTANT_KEY_ADDRESS_NAME, textAddress.text.toString().trim())
                startActivity(intent)
            }
        }
    }

    override fun onGetOrderSuccess(order: Order) {
        val list = order.listOrderItem
        adapter.setData(list)
        binding.apply {
            textName.setText(order.address.name)
            textPhone.setText(order.address.phone)
            textAddress.setText(order.address.address)
            textCode.setText(order.id.toString())
            textOrderTime.setText(order.order_time)
            textTotalPrice.setText(order.total_price.formatAsNumber())

            if(order.payment.payment_type == 0L) textPaymentType.setText(R.string.tien_mat)
            else textPaymentType.setText(R.string.thanh_toan_paypal)

            var noteStr = ""
            for (orderItem in list) {
                if (orderItem.note.isNotEmpty()) {
                    noteStr += "${orderItem.name}" +": "+"${orderItem.note}\n"
                }
            }
            if(noteStr.isNotEmpty()) textNote.setText(noteStr);
        }
    }

    override fun onGetAddressStoreSuccess(address: Address) {
        binding.linerAddressStore.visibility = View.VISIBLE
        binding.apply {
            textNameStore.text = address.name
            textAddressAdmin.text = address.address
        }
    }

    override fun onGetAddressStoreFail() {
        binding.linerAddressStore.visibility = View.GONE
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}