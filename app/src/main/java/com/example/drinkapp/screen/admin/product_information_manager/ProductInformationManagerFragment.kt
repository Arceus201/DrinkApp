package com.example.drinkapp.screen.admin.product_information_manager

import android.content.Intent
import com.example.drinkapp.databinding.AdminFragmentProductInformationManagerBinding
import com.example.drinkapp.screen.admin.category.CategoryActivity
import com.example.drinkapp.screen.admin.product.ProductActivity
import com.example.drinkapp.screen.admin.revenuestatistics.RevenueStatisticsActivity
import com.example.drinkapp.screen.admin.size.SizeActivity
import com.example.drinkapp.utils.base.BaseFragment

class ProductInformationManagerFragment:BaseFragment<AdminFragmentProductInformationManagerBinding>
    (AdminFragmentProductInformationManagerBinding::inflate) {
    override fun initView() {
    }

    override fun initData() {
    }

    override fun handleEvent() {
        binding.apply {
            buttonToCategory.setOnClickListener {
                val intent = Intent(context,CategoryActivity::class.java)
                startActivity(intent)
            }
            buttonToSize.setOnClickListener {
                val intent = Intent(context, SizeActivity::class.java)
                startActivity(intent)
            }
            buttonToProduct.setOnClickListener {
                val intent = Intent(context, ProductActivity::class.java)
                startActivity(intent)
            }
            buttonToStatistics.setOnClickListener {
                val intent = Intent(context, RevenueStatisticsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}