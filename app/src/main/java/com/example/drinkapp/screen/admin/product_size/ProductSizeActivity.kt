package com.example.drinkapp.screen.admin.product_size

import android.R
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.call.CallApiPriceSize
import com.example.drinkapp.data.resource.call.CallApiSize
import com.example.drinkapp.databinding.AdminActivityProductSizeBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewPriceSizeAdapter
import com.example.drinkapp.screen.admin.product.ProductActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemPriceSizeClickListener
import com.example.drinkapp.utils.setMaxLength

class ProductSizeActivity :
    BaseActivity<AdminActivityProductSizeBinding>(AdminActivityProductSizeBinding::inflate),
    ProductSizeContract.View,
    OnItemPriceSizeClickListener {

    private lateinit var presenter: ProductSizePresenter
    private var data: List<Long>? = null
    private lateinit var listPriceSize: List<PriceSize>
    private var adapterPS = RecyclerViewPriceSizeAdapter(this)
    private var sizeId: Long = 1
    private lateinit var product: Product

    override fun initView() {
        binding.apply {
            recyclerViewPS.adapter = adapterPS
            textPrice.setMaxLength(19)
        }
    }

    override fun initData() {
        product = intent.getSerializableExtra(Constant.KEY_PRODUCT) as Product
        presenter =
            ProductSizePresenter(this, CallApiSize.getInstance(), CallApiPriceSize.getInstance())
        presenter.getALLSize()
        presenter.getAllPriceSize(product.id)
    }

    override fun handleEvent() {
        binding.apply {
            spinnerSizes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sizeId = data?.get(position)!!
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    sizeId = data?.get(0)!!
                }
            }
            buuttonAddUpdate.setOnClickListener {
                buuttonAddUpdate.isEnabled = false
                val checked = CheckSize(sizeId, listPriceSize)
                if (checked != null) {
                    presenter.updatePriceSize(
                        checked.id,
                        product.id,
                        sizeId,
                        binding.textPrice.text.toString().toDouble(),
                        checked.status
                    )
                } else {
                    presenter.addPriceSize(
                        product.id,
                        sizeId,
                        binding.textPrice.text.toString().toDouble()
                    )
                }
            }
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    fun CheckSize(code: Long, list: List<PriceSize>): PriceSize? {
        val priceSize = list.find { it.size.id == code }
        return priceSize
    }

    override fun onGetAllSizeSuccess(result: List<Pair<Long, String>>) {
        data = result.drop(1).map { it.first }
        val adapter =
            ArrayAdapter(this, R.layout.simple_spinner_item, result.drop(1).map { it.second })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSizes.adapter = adapter
    }

    override fun onGetAllPriceSizeSucess(result: List<PriceSize>) {
        adapterPS.setData(result)
        listPriceSize = result
    }

    override fun onUpdateAndAddSuccess(result: List<PriceSize>) {
        binding.buuttonAddUpdate.isEnabled = true
        adapterPS.setData(result)
        listPriceSize = result
        binding.textPrice.setText("")
    }


    override fun onFail(msg: String) {
        binding.buuttonAddUpdate.isEnabled = true
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onGetAllPriceSizeFail() {
        adapterPS.clearData()
    }

    override fun onItemHideShowPSClick(priceSize: PriceSize) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(MESS_CONFIRM_STATUS_PRICESIZE_TITLE)
        alertDialog.setMessage(MESS_CONFIRM_STATUS_PRICESIZE_MESSAGE)
        alertDialog.setPositiveButton(com.example.drinkapp.R.string.ok) { dialog, which ->
            presenter.updatePriceSize(
                priceSize.id, priceSize.product.id,
                priceSize.size.id, priceSize.price, priceSize.status
            )
        }
        alertDialog.setNegativeButton(com.example.drinkapp.R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    companion object {
        const val MESS_CONFIRM_STATUS_PRICESIZE_TITLE = "Xác nhận thay đổi trạng thái"
        const val MESS_CONFIRM_STATUS_PRICESIZE_MESSAGE = "Bạn có chắc muốn thay đổi trạng thái"

    }

}