package com.example.drinkapp.screen.admin.main

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.drinkapp.R
import com.example.drinkapp.databinding.AdminActivityMainBinding
import com.example.drinkapp.screen.admin.custom_manager.CustomManagerFragement
import com.example.drinkapp.screen.admin.order_manager.OrderManagerFragment
import com.example.drinkapp.screen.admin.product_information_manager.ProductInformationManagerFragment
import com.example.drinkapp.screen.common.person.PersonFragment
import com.example.drinkapp.utils.ViewPagerAdapter
import com.example.drinkapp.utils.base.BaseActivity

class MainAdminActivity :
    BaseActivity<AdminActivityMainBinding>(AdminActivityMainBinding::inflate) {
    override fun initView() {
        //TODO("Not yet implemented")
    }

    override fun initData() {
        //TODO("Not yet implemented")
    }

    override fun handleEvent() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(
                supportFragmentManager,
                listOf<Fragment>(
                    OrderManagerFragment(),
                    ProductInformationManagerFragment(),
                    CustomManagerFragement(),
                    PersonFragment()
                )
            )
            navigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_order_manager -> {
                        binding.viewPager.currentItem = 0
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_product_manager -> {
                        binding.viewPager.currentItem = 1
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_customer_manager -> {
                        binding.viewPager.currentItem = 2
                        return@setOnItemSelectedListener true
                    }
                    else -> {
                        binding.viewPager.currentItem = 3
                        return@setOnItemSelectedListener true
                    }
                }
            }
            viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    //TODO("Not yet implemented")
                }

                override fun onPageSelected(position: Int) {
                    if(position == 0) navigation.menu.findItem(R.id.menu_order_manager).isChecked = true
                    else if (position == 1) navigation.menu.findItem(R.id.menu_product_manager).isChecked = true
                    else if(position == 2) navigation.menu.findItem(R.id.menu_customer_manager).isChecked = true
                    else navigation.menu.findItem(R.id.menu_person_admin).isChecked = true
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //TODO("Not yet implemented")
                }

            })
        }
    }
}