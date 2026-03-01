package com.example.drinkapp.screen.common.address

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.databinding.ActivityChoseAddressBinding
import com.example.drinkapp.screen.common.addaddress.AddAddressActivity
import com.example.drinkapp.screen.client.adapter.RecyclerViewAddressAdapter
import com.example.drinkapp.screen.client.confirm_order.ConfirmOrderActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemAddressClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressActivity :
    BaseActivity<ActivityChoseAddressBinding>(ActivityChoseAddressBinding::inflate),
    AddressContract.View,
    OnItemAddressClickListener {
    
    @Inject
    lateinit var presenter: AddressPresenterCoroutine
    
    private lateinit var adapter: RecyclerViewAddressAdapter
    private var listChose: MutableList<CartItem> = mutableListOf()
    private var totalPrice: String = "null"
    
    override fun initView() {
        if(intent!=null) {
            listChose =
                (intent.getSerializableExtra(Constant.CART_ITEM_LIST) as? ArrayList<CartItem>)
                    ?: mutableListOf()
            totalPrice = intent.getStringExtra(Constant.TOTAL_PRICE).toString()
        }
        var status = -1
        val titleText = if(totalPrice.equals("null")){
            status = -1
            TEXT_NAME_PAGE
        }else{
            status = 1
            TEXT_CHOSE_ADDRESS
        }
        
        // Setup CommonHeaderView
        binding.commonHeader.configure {
            title = titleText
            showBackButton = true
            onBackClick = {
                if(listChose.isEmpty() or totalPrice.equals("-1")){
                    finish()
                }else{
                    val intent = Intent(this@AddressActivity,ConfirmOrderActivity::class.java)
                    intent.putExtra(Constant.CART_ITEM_LIST, ArrayList(listChose))
                    intent.putExtra(Constant.TOTAL_PRICE,totalPrice)
                    startActivity(intent)
                    finish()
                }
            }
        }
        
        adapter = RecyclerViewAddressAdapter(this,status)
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        var userData = UserManager.getUserInfo(this)
        presenter.attachView(this)
        userData.id?.let { presenter.getAllAddress(it) }
    }

    override fun handleEvent() {
        binding.apply {
            buttonAddAddress.setOnClickListener {
                val intent = Intent(this@AddressActivity, AddAddressActivity::class.java)
                if(listChose.isEmpty() or totalPrice.equals("-1")){
                    finish()
                }else {
                    intent.putExtra(Constant.CART_ITEM_LIST, ArrayList(listChose))
                    intent.putExtra(Constant.TOTAL_PRICE, totalPrice)
                }
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onItemChoseAddressClick(address: Address) {
        if(!listChose.isEmpty() and  !totalPrice.equals("null")){
            val intent = Intent(this,ConfirmOrderActivity::class.java)
            intent.putExtra(Constant.KEY_ADDRESS,address)
            intent.putExtra(Constant.CART_ITEM_LIST, ArrayList(listChose))
            intent.putExtra(Constant.TOTAL_PRICE,totalPrice)
            startActivity(intent)
            finish()
        }
    }

    override fun onGetAllAdressSuccess(list: List<Address>) {
        adapter.setData(list)
    }

    override fun onFail() {
        adapter.clearData()
    }

    override fun onDestroy() {
        presenter.onStop()
        super.onDestroy()
    }

    companion object{
        const val TEXT_CHOSE_ADDRESS= "Chọn địa chỉ nhận hàng"
        const val TEXT_NAME_PAGE= "Quản lý địa chỉ"
    }
}