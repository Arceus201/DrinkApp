package com.example.drinkapp.screen.admin.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.databinding.AdminActivityProductBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewDrinkAdapter
import com.example.drinkapp.screen.admin.main.MainAdminActivity
import com.example.drinkapp.screen.admin.product_add.ProductAddActivity
import com.example.drinkapp.screen.admin.product_size.ProductSizeActivity
import com.example.drinkapp.screen.admin.product_update.ProductUpdateActivity
import com.example.drinkapp.screen.admin.waiting_for_delivery.WattingDeliveryActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemEditDrinkClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductActivity :
    BaseActivity<AdminActivityProductBinding>(AdminActivityProductBinding::inflate),
    ProductContract.View,
    OnItemEditDrinkClickListener {

    @Inject
    lateinit var presenterCoroutine: ProductPresenterCoroutine
    private val adapter: RecyclerViewDrinkAdapter = RecyclerViewDrinkAdapter(this)
    private lateinit var listP: MutableList<Product>

    override fun initView() {
        binding.apply {
            // Configure CommonHeaderView
            commonHeader.configure {
                title = getString(R.string.page_product_manager)
                showBackButton = true
                onBackClick = { finish() }
            }
            
            recyclerview.adapter = adapter
        }
    }

    override fun initData() {
        presenterCoroutine.attachView(this)
        presenterCoroutine.getAllDrink()
    }

    override fun onResume() {
        super.onResume()
        presenterCoroutine.getAllDrink()
    }

    override fun handleEvent() {
        binding.apply {
            buttonAddProduct.setOnClickListener {
                val intent = Intent(applicationContext,ProductAddActivity::class.java)
                startActivity(intent)
            }
            buttonSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val filteredList = filterProductList(newText)
                    adapter.setData(filteredList)
                    return true
                }
            })
        }
    }
    private fun filterProductList(query: String?): List<Product> {
        val filteredList = ArrayList<Product>()
        if (query.isNullOrBlank()) {
            filteredList.addAll(listP)
        } else {
            for (product in listP) {
                if (product.name.contains(query, ignoreCase = true)) {
                    filteredList.add(product)
                }
            }
        }
        return filteredList
    }

    override fun showAllDrinkSuccess(list: List<Product>) {
        listP = list as MutableList<Product>
        adapter.setData(list)
    }

    override fun onUpdateSuccess(msg: String) {
        presenterCoroutine.getAllDrink()
    }

    override fun onDeleteProductSuccess(msg: String) {
       onResume()
    }

    override fun showAllDrinkFail() {
       adapter.clear()
    }


    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemDelete(id:Long) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(MESS_CONFIRM_DELETE_TITLE)
        alertDialog.setMessage(MESS_CONFIRM_DELETE_MESSAGE)
        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            presenterCoroutine.deleteProduct(id)
        }
        alertDialog.setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onItemEditSizeClick(product: Product) {
        val intent = Intent(this,ProductSizeActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constant.KEY_PRODUCT, product)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onItemEditClick(product: Product) {
        val intent = Intent(this,ProductUpdateActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constant.KEY_PRODUCT, product)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenterCoroutine.onStop()
        super.onDestroy()
    }

    companion object{
        const val MESS_CONFIRM_DELETE_TITLE = "Xác nhận xóa"
        const val MESS_CONFIRM_DELETE_MESSAGE = "Bạn có chắc muốn xóa đồ uống"

    }
}