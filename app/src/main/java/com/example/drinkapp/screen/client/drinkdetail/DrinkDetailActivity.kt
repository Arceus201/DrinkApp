package com.example.drinkapp.screen.client.drinkdetail

import android.R
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.call.CallApiCartItem
import com.example.drinkapp.data.resource.call.CallApiDrink
import com.example.drinkapp.data.resource.call.CallApiPriceSize
import com.example.drinkapp.databinding.ClientActivityDrinkDetailBinding
import com.example.drinkapp.screen.client.cart.CartActivity
import com.example.drinkapp.screen.client.main.MainActivity
import com.example.drinkapp.utils.*
import com.example.drinkapp.utils.base.BaseActivity

class DrinkDetailActivity :
    BaseActivity<ClientActivityDrinkDetailBinding>(ClientActivityDrinkDetailBinding::inflate),
    DrinkDetailContract.View {
    private lateinit var presenter: DrinkDetailPresenter
    private var price: Double? = 0.0
    private var pricesize_Id: Long = 1
    private var user_Id: Long? = null

    private lateinit var data: List<Long>
    override fun initView() {
        binding.apply {
            textNote.setMaxLength(255)
        }
    }

    override fun initData() {
        var userData = UserManager.getUserInfo(applicationContext)
        user_Id = userData.id
        presenter = DrinkDetailPresenter(
            this,
            CallApiDrink.getInstance(),
            CallApiPriceSize.getInstance(),
            CallApiCartItem.getInstance()
        )
        intent?.getLongExtra(Constant.KEY_PRODUCT_ID, 0)?.let { productId ->
            presenter?.getProductById(productId)
            presenter.getListPriceSize(productId)
        }

    }

    override fun handleEvent() {
        binding.apply {
            buttonSub.setOnClickListener {
                presenter.changeQuantity(textQuantity.text.toString().toLong(), -1L)
            }
            buttonPlus.setOnClickListener {
                presenter.changeQuantity(textQuantity.text.toString().toLong(), 1L)
            }
            spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    pricesize_Id = data.get(position)
                    val selectedItem = parent?.getItemAtPosition(position) as String
                    price = extractPrice(selectedItem)
                    if (price != null) {
                        binding.textSumPrice.text =
                            (price!! * (binding.textQuantity.text.toString().toLong())).formatAsNumber()
                        binding.textPricePlus.text =
                            "+" + (price!! - (binding.textPrice.text.toString().parseFromNumberFormat())).formatAsNumber() + " đ"
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    pricesize_Id = data.get(0)
                    val selectedItem = parent?.getItemAtPosition(0) as String
                    price = extractPrice(selectedItem)
                    if (price != null) {
                        binding.textSumPrice.text =
                            (price!! * (binding.textQuantity.text.toString().toLong())).formatAsNumber()
                        binding.textPricePlus.text =
                            "+" + (price!! - (binding.textPrice.text.toString().parseFromNumberFormat())).formatAsNumber() + " đ"
                    }
                }
            }
            buttonBack.setOnClickListener {
                finish()
            }
            buttonAddToCart.setOnClickListener {
                buttonAddToCart.isEnabled = false
                user_Id?.let { it1 -> presenter.checkCartItem(it1, pricesize_Id) }
            }
        }
    }

    override fun onGetProductSuccess(product: Product) {
        binding.apply {
            textName.setText(product.name)
            textSold.setText(product.quantitysold.toString())
            Glide.with(imageButton.context)
                .load(product.image)
                .into(imageButton)
            textPrice.setText(product.price.formatAsNumber())
        }
    }

    fun extractPrice(originalString: String): Double? {
        val parts = originalString.split(" Giá ")

        if (parts.size == 2) {
            val priceAndUnit = parts[1].split(" đ")

            if (priceAndUnit.size == 2) {
                return priceAndUnit[0].parseFromNumberFormat()
            }
        }

        return null
    }

    override fun onChangeQuantitySuccess(index: Long) {
        binding.textQuantity.setText(index.toString())
        if (price != null) {
            binding.textSumPrice.text =
                (price!! * (binding.textQuantity.text.toString().toLong())).formatAsNumber()
        }
    }

    override fun onGetListPriceSizeSuccess(list: List<Pair<Long, String>>) {
        data = list.map { it.first }
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, list.map { it.second })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSize.adapter = adapter
    }

    override fun onCheckCartItemSuccess(cartItem: CartItem) {
        val note = binding.textNote.text.toString().trim()
        if (note.equals("")) {
            presenter.updateCartItemNumber(
                cartItem.id,
                (binding.textQuantity.text.toString().toLong()) + cartItem.number
            )
        }
        else {
            presenter.updateCartItemUpdate(
                cartItem.id,
                (binding.textQuantity.text.toString().toLong()) + cartItem.number,
                note
            )
        }
    }

    override fun onAddToCartSuccess(cartItem: CartItem) {
        binding.buttonAddToCart.isEnabled = true
        NextBackPage(this).startActivity(CartActivity::class.java)
        finish()
    }

    override fun onFail(msg: String) {
        binding.buttonAddToCart.isEnabled = true
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCheckFail() {
        user_Id?.let {
            presenter.addCartItem(
                pricesize_Id,
                it,
                binding.textQuantity.text.toString().toLong(),
                binding.textNote.text.toString().trim()
            )
        }
    }

}