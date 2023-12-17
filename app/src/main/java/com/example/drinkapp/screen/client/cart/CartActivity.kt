package com.example.drinkapp.screen.client.cart

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.drinkapp.R
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.resource.call.CallApiCartItem
import com.example.drinkapp.databinding.ClientActivityCartBinding
import com.example.drinkapp.screen.client.adapter.RecyclerViewCartAdapter
import com.example.drinkapp.screen.client.confirm_order.ConfirmOrderActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemCartClickListener

class CartActivity : BaseActivity<ClientActivityCartBinding>(ClientActivityCartBinding::inflate),
    CartContract.View ,
    OnItemCartClickListener{
    private lateinit var presenter: CartPresenter
    private val adapter: RecyclerViewCartAdapter = RecyclerViewCartAdapter(this)
    private lateinit var listC: MutableList<CartItem>
    private var listChoseCartItem: MutableList<CartItem> = mutableListOf()

    private val checkList = mutableListOf<Pair<Long, Boolean>>()

    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        val userData = UserManager.getUserInfo(this)
        presenter = CartPresenter(this, CallApiCartItem.getInstance())
        userData.id?.let { presenter.getAllCartItemByUserID(it) }
    }

    override fun handleEvent() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
            buttonDeleteAll.setOnClickListener{
                val alertDialog = AlertDialog.Builder(it.context)
                alertDialog.setTitle(R.string.confirm_delete)
                alertDialog.setMessage(R.string.how_to_confirm_delete)
                alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
                    val userData = UserManager.getUserInfo(applicationContext)
                    userData.id?.let { it1 -> presenter.deleteAll(it1) }
                }
                alertDialog.setNegativeButton(R.string.cancel) { dialog, which ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
            buttonAddToCart.setOnClickListener {
                if(listChoseCartItem.size != 0){
                    val intent = Intent(application, ConfirmOrderActivity::class.java)
                    intent.putExtra(Constant.CART_ITEM_LIST, ArrayList(listChoseCartItem))
                    intent.putExtra(Constant.TOTAL_PRICE,binding.textSumPrice.text.toString())
                    startActivity(intent)
                    finish()
                } else {
                    onFail(MESS_NO_PRODUCTS_SELECTED)
                }
            }
        }
    }

    override fun onDisplayAllCartSuccess(list: List<CartItem>) {
        listC = list as MutableList<CartItem>
        for (cartItem in listC) {
            val idCartItem = cartItem.id
            checkList.add(Pair(idCartItem, false))
        }
        adapter.setData(list)
    }

    override fun onUpdateCartNumberSuccess(cartItem: CartItem) {
        val indexToReplace = listC.indexOfFirst { it.id == cartItem.id }
        if (indexToReplace != -1) {
            listC.set(indexToReplace, cartItem)
        }
        adapter.setData(listC)
        TotalPrice()
    }

    override fun onDeleteCartItemByIdSuccess(id_cartItem: Long) {
        val indexToReplace = listC.indexOfFirst { it.id == id_cartItem }
        if (indexToReplace != -1) {
            listC.removeAt(indexToReplace)
            checkList.removeIf { it.first == id_cartItem}
            TotalPrice()
        }
        adapter.setData(listC)
    }

    override fun onDeleteAllSuccess() {
        listC.clear()
        checkList.clear()
        adapter.setData(listC)
        TotalPrice()
    }

    override fun onItemSubQuantityClick(id: Long, number: Long) {
        if(number == 1L) onFail(MESS_INVALID_QUANTITY)
        else presenter.updateCartItemNumber(id,number-1L)
    }

    override fun onItemAddQuantityClick(id: Long, number: Long) {
        presenter.updateCartItemNumber(id,number+1L)
    }

    override fun onItemDeleteClick(id: Long) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.confirm_delete)
        alertDialog.setMessage(R.string.how_to_confirm_delete)
        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            presenter.deleteCartItemById(id)
        }
        alertDialog.setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onItemChoseClick(id_cartItem: Long,isChecked: Boolean) {
        val index = checkList.indexOfFirst { it.first == id_cartItem }
        if (index != -1) {
            checkList[index] = Pair(id_cartItem, isChecked)
        }else{
            checkList.add(Pair(id_cartItem,isChecked))
        }
        TotalPrice()
    }
    fun TotalPrice(){
        listChoseCartItem.clear()
        val checkedItems = checkList.filter { it.second }
        val idList = checkedItems.map { it.first }
        var sum: Double = 0.0
        for (id in idList) {
            val cartItem = listC.find { it.id == id }
            if (cartItem != null) {
                sum += cartItem.priceSize.price * cartItem.number
                listChoseCartItem.add(cartItem)
            }
        }
        binding.textSumPrice.setText(sum.formatAsNumber())
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDisplayAllCartFail() {
        adapter.clearData()
    }

companion object{
    const val MESS_INVALID_QUANTITY = "So luong phai > 0"
    const val MESS_NO_PRODUCTS_SELECTED = "không có sản phẩm nào được chọn"
}
}