package com.example.drinkapp.screen.client.confirm_order

import android.content.Intent
import android.view.View
import com.example.drinkapp.R
import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.databinding.ClientActivityConfirmOrderBinding
import com.example.drinkapp.screen.common.address.AddressActivity
import com.example.drinkapp.screen.client.adapter.RecyclerViewConfirmOderAdapter
import com.example.drinkapp.screen.client.cart.CartActivity
import com.example.drinkapp.screen.client.order_success.OrderSuccessActivity
import com.example.drinkapp.utils.base.BaseActivity
import android.widget.Toast
import com.example.drinkapp.data.resource.call.ExchangeRateAPI
import com.example.drinkapp.utils.*
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.OrderRequest
import com.paypal.checkout.order.PurchaseUnit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ConfirmOrderActivity :
    BaseActivity<ClientActivityConfirmOrderBinding>(ClientActivityConfirmOrderBinding::inflate),

    ConfirmOrderContract.View {
    private lateinit var presenter: ConfirmOrderPresenter
    private val adapter: RecyclerViewConfirmOderAdapter = RecyclerViewConfirmOderAdapter()
    private lateinit var listChose: MutableList<CartItem>
    private lateinit var totalPrice: String
    private var addressGet: Address? = null
    private var payment_type: Long = -1
    override fun initView() {
        binding.recyclerView.adapter = adapter
        totalPrice = intent.getSerializableExtra(Constant.TOTAL_PRICE) as String
        binding.textTotalPrice.setText(totalPrice)

        if (totalPrice.parseFromNumberFormat() >= 200000.0) {
            binding.textCheck.visibility = View.VISIBLE
            binding.radioCash.visibility = View.GONE
        }
    }

    override fun initData() {
        listChose = (intent.getSerializableExtra(Constant.CART_ITEM_LIST) as? ArrayList<CartItem>)
            ?: mutableListOf()
        adapter.setData(listChose)

        val get_address = intent.getParcelableExtra<Address>(Constant.KEY_ADDRESS)
        if (get_address != null) {
            addressGet = get_address
            binding.apply {
                textName.setText(addressGet?.name)
                texttPhone.setText(addressGet?.phone)
                textAddress.setText(addressGet?.address)
            }
        }

        presenter = ConfirmOrderPresenter(
            this,
            ExchangeRateAPI.getInstance(),
            CallApiOrder.getInstance()
        )
    }

    override fun handleEvent() {
        binding.apply {
            buttonBack.setOnClickListener {
                val intent = Intent(applicationContext, CartActivity::class.java)
                startActivity(intent)
                finish()
            }
            buttonEditAddress.setOnClickListener {
                val intent = Intent(applicationContext, AddressActivity::class.java)
                intent.putExtra(Constant.CART_ITEM_LIST, ArrayList(listChose))
                intent.putExtra(Constant.TOTAL_PRICE, totalPrice)
                startActivity(intent)
                finish()
            }
            radioGroup.setOnCheckedChangeListener { group, checkId ->
                when (checkId) {
                    R.id.radio_Cash -> {
                        if(addressGet != null) {
                            payment_type = 0
                            layoutPaypal.visibility = View.GONE
                            layoutButtonPaypal.visibility = View.GONE
                            confirmButton.visibility = View.VISIBLE
                            textTotalPrice.setText(totalPrice)
                            textDonVi.setText(R.string.vnd)
                        }else{
                            onFail(MESS_CHOSE_ADDRESS)
                        }
                    }
                    R.id.radio_Transfer -> {
                        if(addressGet != null) {
                            payment_type = 1
                            layoutPaypal.visibility = View.VISIBLE
                            layoutButtonPaypal.visibility = View.VISIBLE
                            confirmButton.visibility = View.GONE
                            presenter.getExchangeRate()

                        }else{
                            onFail(MESS_CHOSE_ADDRESS)
                        }
                    }
                    else -> {
                        payment_type = -1
                    }
                }
            }


            confirmButton.setOnClickListener {
                confirmButton.isEnabled = false
                if (addressGet != null && payment_type != -1L) {
                    val currentTime = Date()
                    val order_time = currentTime.formatTimeCurrent()
                    presenter.addOrder(
                        listChose,
                        addressGet!!.id,
                        order_time,
                        totalPrice.parseFromNumberFormat(),
                        0L,
                        0L
                    )
                } else {
                    onFail(getString(R.string.KEY_MISSING_INFORMATION))
                }
            }
            paymentButtonContainer.setup(
                createOrder =
                CreateOrder { createOrderActions ->
                    val order =
                        OrderRequest(
                            intent = OrderIntent.CAPTURE,
                            appContext = AppContext(userAction = UserAction.PAY_NOW),
                            purchaseUnitList =
                            listOf(
                                PurchaseUnit(
                                    amount =
                                    Amount(currencyCode = CurrencyCode.USD, value = binding.textTotalPrice.text.toString())
                                )
                            )
                        )
                    createOrderActions.create(order)
                },
                onApprove =
                OnApprove { approval ->
                    approval.orderActions.capture { captureOrderResult ->
                        approval.orderActions.capture { captureOrderResult ->
                            val currentTime = Date()
                            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            val order_time = simpleDateFormat.format(currentTime)
                            presenter.addOrder(
                                listChose,
                                addressGet!!.id,
                                order_time,
                                totalPrice.parseFromNumberFormat(),
                                1L,
                                1L
                            )
                        }
                    }
                },
                onCancel = OnCancel {
                    onFail(MESS_PAYMENT_CANCEL)
                },
                onError = OnError { errorInfo ->
                    onFail(MESS_PAYMENT_ERROR)
                }
            )

        }
    }


    override fun onAddOrderSuccess(id: Long) {
        binding.confirmButton.isEnabled = true
        val intent = Intent(applicationContext, OrderSuccessActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER, id)
        startActivity(intent)
        finish()
    }

    override fun onGetExChangeRateSuccess(VND: Double) {
        val usdToVndRate = VND
        if (usdToVndRate != null) {
            binding.apply {
                val amountInUsd = usdToVndRate.format(totalPrice)
                textNetAmount.setText(amountInUsd.toString())
                val paypalFee = amountInUsd * 0.044 + 0.3
                textPaypalFee.setText(String.format("%.2f", paypalFee))
                val totalPaypal = paypalFee + amountInUsd
                textTotalPrice.setText(String.format("%.2f", totalPaypal))
                textDonVi.setText(R.string.USD)
            }
        }

    }

    override fun onFail(msg: String) {
        binding.confirmButton.isEnabled = true
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    companion object{
        const val MESS_PAYMENT_CANCEL ="thanh toán bị hủy"
        const val MESS_PAYMENT_ERROR ="thanh toán bị lỗi"
        const val MESS_CHOSE_ADDRESS = "bạn cần chọn địa chỉ trước khi thanh toán"
    }

}