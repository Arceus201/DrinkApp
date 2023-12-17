package com.example.drinkapp.screen.common.addaddress

import android.R
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.model.addressVN.City
import com.example.drinkapp.data.model.addressVN.District
import com.example.drinkapp.data.model.addressVN.Ward
import com.example.drinkapp.data.resource.call.CallApiAddress
import com.example.drinkapp.data.resource.call.CallApiAddressVN
import com.example.drinkapp.databinding.ActivityAddressBinding
import com.example.drinkapp.screen.common.address.AddressActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.setMaxLength

class AddAddressActivity : BaseActivity<ActivityAddressBinding>(ActivityAddressBinding::inflate),
    AddAddressContract.View {
    private lateinit var presenter: AddAddressPresenter
    private var listChose: MutableList<CartItem> = mutableListOf()
    private var totalPrice: String = "-1"
    private var dataDistrict: List<List<District>>? = null
    private var dataWard: List<List<Ward>>? = null
    private var user_Id: Long? = null
    private var cityName: String = ""
    private var districtName: String = ""
    private var wardName: String = ""
    override fun initView() {
        binding.apply {
            textUsername.setMaxLength(25)
            textPhone.setMaxLength(11)
            textAddress.setMaxLength(150)
        }
    }

    override fun initData() {
        if (intent != null) {
            listChose =
                (intent.getSerializableExtra(Constant.CART_ITEM_LIST) as? ArrayList<CartItem>)
                    ?: mutableListOf()
            totalPrice = intent.getStringExtra(Constant.TOTAL_PRICE).toString()
        }

        var userData = UserManager.getUserInfo(applicationContext)
        user_Id = userData.id
        presenter = AddAddressPresenter(
            this,
            CallApiAddress.getInstance(),
            CallApiAddressVN.getInstance()
        )
        presenter.getAllAddressVN()
    }

    override fun handleEvent() {
        binding.apply {
            spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    cityName = parent?.getItemAtPosition(position) as String
                    val listDistrict = dataDistrict?.get(position)!!
                    setDataSinnerDistrict(listDistrict)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    cityName = parent?.getItemAtPosition(0) as String
                    val listDistrict = dataDistrict?.get(0)!!
                    setDataSinnerDistrict(listDistrict)
                }
            }

            spinnerDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (dataWard != null) {
                        districtName = parent?.getItemAtPosition(position) as String
                        val listWard = dataWard?.get(position)!!
                        setDataSinnerWard(listWard)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    if (dataWard != null) {
                        cityName = parent?.getItemAtPosition(0) as String
                        val listWard = dataWard?.get(0)!!
                        setDataSinnerWard(listWard)
                    }

                }
            }
            spinnerWard.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    wardName = parent?.getItemAtPosition(position) as String

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                    wardName = parent?.getItemAtPosition(0) as String

                }
            }


            buttonSave.setOnClickListener {
                val addressFull = binding.textAddress.text.toString().trim() +
                        ", "+wardName+", "+districtName+", "+cityName +", "+ Constant.KEY_CONSTANT_KEY_VIET_NAM
                if(!textUsername.text.toString().trim().isNullOrEmpty()
                    && !textPhone.text.toString().trim().isNullOrEmpty()
                    && !binding.textAddress.text.toString().trim().isNullOrEmpty()){
                    user_Id?.let { it1 ->
                        presenter.addAddress(
                            it1,
                            textUsername.text.toString().trim(),
                            textPhone.text.toString().trim(),
                            addressFull
                        )
                    }
                }else{
                    onFail(KEY_MISSING_INFORMATION)
                }
            }
            buttonBack.setOnClickListener {
                val intent = Intent(this@AddAddressActivity, AddressActivity::class.java)
                if (listChose.isEmpty() or totalPrice.equals("-1")) {
                    finish()
                } else {
                    intent.putExtra(Constant.CART_ITEM_LIST, ArrayList(listChose))
                    intent.putExtra(Constant.TOTAL_PRICE, totalPrice)
                }
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onAddSuccess(address: Address) {
        val intent = Intent(this@AddAddressActivity, AddressActivity::class.java)
        if (listChose.isEmpty() or totalPrice.equals("-1")) {
            finish()
        } else {
            intent.putExtra(Constant.CART_ITEM_LIST, ArrayList(listChose))
            intent.putExtra(Constant.TOTAL_PRICE, totalPrice)
        }
        startActivity(intent)
        finish()
    }

    override fun onGetAllAddressVnSuccess(list: List<City>) {
        setDataSpinnerCity(list)
    }

    override fun onGetAllAddressVnFail() {
        //TODO("Not yet implemented")
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun setDataSpinnerCity(list: List<City>) {
        if (list != null && list.isNotEmpty()) {
            val data: List<Pair<String, List<District>>> = list
                .filter { it.name != null && it.districts != null }
                .map { it.name!! to it.districts!! }
            dataDistrict = data.map { it.second }
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, data.map { it.first })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCity.adapter = adapter
        }
    }

    fun setDataSinnerDistrict(list: List<District>) {
        if (list != null && list.isNotEmpty()) {
            val data: List<Pair<String, List<Ward>>> = list
                .filter { it.name != null && it.wards != null }
                .map { it.name!! to it.wards!! }
            dataWard = data.map { it.second }
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, data.map { it.first })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDistrict.adapter = adapter
        }
    }

    fun setDataSinnerWard(list: List<Ward>) {
        if (list != null && list.isNotEmpty()) {
            val data: List<String> = list
                .filter { it.name != null }
                .map { it.name!! }
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, data)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerWard.adapter = adapter
        }
    }
    companion object{
        const val KEY_MISSING_INFORMATION = "bạn cần nhập đầy đủ các trường thông tin"
    }
}