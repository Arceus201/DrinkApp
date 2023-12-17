package com.example.drinkapp.screen.admin.size

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.call.CallApiSize
import com.example.drinkapp.databinding.AdminActivityCategoryBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewSizeAdapter
import com.example.drinkapp.screen.admin.main.MainAdminActivity
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemSizeClickListener
import com.example.drinkapp.utils.setMaxLength

class SizeActivity :
    BaseActivity<AdminActivityCategoryBinding>(AdminActivityCategoryBinding::inflate),
    SizeContract.View,
    OnItemSizeClickListener {

    private lateinit var presenter: SizePresenter
    private val adapter = RecyclerViewSizeAdapter(this)
    private lateinit var sizeUpdate : Size

    override fun initView() {
        binding.apply {
            recyclerview.adapter = adapter
            textNamePage.setText(PAGE_NAME)
            textName.setMaxLength(25)
        }
    }

    override fun initData() {
        presenter = SizePresenter(this,CallApiSize.getInstance())
        presenter?.getAllSize()
    }

    override fun handleEvent() {
        binding.apply {
            buttonSave.setOnClickListener {
                buttonSave.isEnabled = false
                if(!binding.textName.text.isNullOrEmpty()) {
                    if (buttonSave.text.toString() == TEXT_SAVE) {
                        presenter?.addSize(textName.text.toString().trim())
                    } else {
                        sizeUpdate.name = binding.textName.text.toString()
                        presenter?.updateSize(sizeUpdate.id, sizeUpdate)
                    }
                }else{
                    displayFail(MESS)
                }
            }
            buttonCancel.setOnClickListener {
                binding.buttonSave.text = TEXT_SAVE
                binding.buttonCancel.visibility = View.GONE
                binding.textName.setText("")
            }
            binding.buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun displaySuccess(list: List<Size>) {
        binding.buttonSave.isEnabled = true
        binding.buttonCancel.visibility = View.GONE
        adapter.setData(list)
        binding.textName.setText("")
    }

    override fun displaySizeFail() {
        adapter.clearData()
    }

    override fun displayFail(msg: String) {
        binding.buttonSave.isEnabled = true
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemSizeClick(pos: Int, size:Size) {
        sizeUpdate = size
        binding.textName.setText(size.name)
       sizeUpdate.id = size.id
        binding.buttonSave.text = TEXT_UPDTAE
        binding.buttonCancel.visibility = View.VISIBLE

    }

    companion object{
        const val PAGE_NAME = "Quản Lý Size"
        const val MESS = "Hãy nhập tên size trước khi save"
        const val TEXT_UPDTAE = "UPDATE"
        const val TEXT_SAVE = "SAVE"
    }
}