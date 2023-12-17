package com.example.drinkapp.screen.shipper.main

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.drinkapp.R
import com.example.drinkapp.databinding.ShipperActivityMainBinding
import com.example.drinkapp.screen.common.person.PersonFragment
import com.example.drinkapp.screen.shipper.orderlist.OrderListFragment
import com.example.drinkapp.utils.ViewPagerAdapter
import com.example.drinkapp.utils.base.BaseActivity

class MainShipperActivity:BaseActivity<ShipperActivityMainBinding>(ShipperActivityMainBinding::inflate) {
    override fun initView() {

    }

    override fun initData() {

    }

    override fun handleEvent() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(
                supportFragmentManager,
                listOf<Fragment>(
                    OrderListFragment(),
                    PersonFragment()
                )
            )
            navigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_shipper_order -> {
                        binding.viewPager.currentItem = 0
                        return@setOnItemSelectedListener true
                    }
                    else -> {
                        binding.viewPager.currentItem = 1
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
                    if (position == 0) navigation.menu.findItem(R.id.menu_shipper_order).isChecked = true
                    else navigation.menu.findItem(R.id.menu_person).isChecked = true
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //TODO("Not yet implemented")
                }

            })
        }
    }
}