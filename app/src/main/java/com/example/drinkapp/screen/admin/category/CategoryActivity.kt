package com.example.drinkapp.screen.admin.category

import android.content.Intent
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.resource.call.CallApiCategory
import com.example.drinkapp.data.resource.response.category.CategoryResponse
import com.example.drinkapp.databinding.AdminActivityCategoryBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewCategoryAdapter
import com.example.drinkapp.screen.admin.main.MainAdminActivity
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemCategoryClickListener
import com.example.drinkapp.utils.setMaxLength

class CategoryActivity :
    BaseActivity<AdminActivityCategoryBinding>(AdminActivityCategoryBinding::inflate),
    CategoryContract.View,
    OnItemCategoryClickListener
{

    private lateinit var presenter: CategoryPresenter
    private val adapter = RecyclerViewCategoryAdapter(this)
    private lateinit var categoryUpdate : Category

    override fun initView() {
        binding.apply {
            recyclerview.adapter = adapter
            textNamePage.setText(PAGE_NAME)
            textName.setMaxLength(25)
        }
    }

    override fun initData() {
        presenter = CategoryPresenter(this,CallApiCategory.getInstance())
        presenter?.getAllCategory()
    }

    override fun handleEvent() {
        binding.apply {
            buttonSave.setOnClickListener {
                buttonSave.isEnabled = false
                if(!binding.textName.text.isNullOrEmpty()) {
                    if (buttonSave.text.toString() == TEXT_SAVE) {
                        presenter?.addCategory(textName.text.toString().trim())
                    } else {
                        categoryUpdate.name = binding.textName.text.toString()
                        presenter?.updateCategory(categoryUpdate.id, categoryUpdate)
                    }
                }
                else{
                    addUpDateFail(MESS)
                }
            }
            buttonCancel.setOnClickListener {
                binding.buttonSave.text = TEXT_SAVE
                binding.buttonCancel.visibility = GONE
                binding.textName.setText("")
            }
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun displaySuccess(list: List<Category>) {
        binding.buttonSave.isEnabled = true
        adapter.setData(list)
        binding.buttonCancel.visibility = GONE
        binding.textName.setText("")
    }

    override fun displayFail() {
        adapter.clearData()
    }

    override fun addUpDateFail(msg: String) {
        binding.buttonSave.isEnabled = true
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemCategoryClick(pos: Int, category: Category) {
        categoryUpdate = category
        binding.textName.setText(category.name)
        categoryUpdate.id = category.id
        binding.buttonSave.text = TEXT_UPDTAE
        binding.buttonCancel.visibility = VISIBLE
    }
    companion object{
        const val PAGE_NAME = "Quản Lý Loại Đồ Uống"
        const val MESS = "Hãy nhập tên loại trước khi save"
        const val TEXT_UPDTAE = "UPDATE"
        const val TEXT_SAVE = "SAVE"
    }
}