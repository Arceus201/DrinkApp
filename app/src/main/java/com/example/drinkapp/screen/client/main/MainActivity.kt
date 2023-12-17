package com.example.drinkapp.screen.client.main



import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.drinkapp.R
import com.example.drinkapp.databinding.ClientActivityMainBinding
import com.example.drinkapp.screen.client.history.HistoryFragment
import com.example.drinkapp.screen.client.home.HomeFragment
import com.example.drinkapp.screen.client.order.OrderFragment
import com.example.drinkapp.screen.common.person.PersonFragment
import com.example.drinkapp.utils.ViewPagerAdapter
import com.example.drinkapp.utils.base.BaseActivity

class MainActivity : BaseActivity<ClientActivityMainBinding>(ClientActivityMainBinding::inflate){
    override fun initView() {
    }

    override fun initData() {

    }

    override fun handleEvent() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(
                supportFragmentManager,
                listOf<Fragment>(
                    HomeFragment(),
                    OrderFragment(),
                    HistoryFragment(),
                    PersonFragment()
                )
            )
            navigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> {
                        binding.viewPager.currentItem = 0
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_order -> {
                        binding.viewPager.currentItem = 1
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_history -> {
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
                    if (position == 0) navigation.menu.findItem(R.id.menu_home).isChecked = true
                    else if(position == 1) navigation.menu.findItem(R.id.menu_order).isChecked = true
                    else if(position == 2) navigation.menu.findItem(R.id.menu_history).isChecked = true
                    else navigation.menu.findItem(R.id.menu_person).isChecked = true
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //TODO("Not yet implemented")
                }

            })
        }
    }

}