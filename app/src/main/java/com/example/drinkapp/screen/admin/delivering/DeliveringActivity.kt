package com.example.drinkapp.screen.admin.delivering

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.databinding.AdminAcitvityDeliveringBinding
import com.example.drinkapp.screen.admin.adapter.RecylerViewOrderManagerAdapter
import com.example.drinkapp.screen.admin.waiting_for_delivery.WattingDeliveryActivity
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemOrderShipperClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeliveringActivity :
    BaseActivity<AdminAcitvityDeliveringBinding>(AdminAcitvityDeliveringBinding::inflate),
    DeliveringContract.View,
    OnItemOrderShipperClickListener {
    private val adapter = RecylerViewOrderManagerAdapter(this)
    @Inject
    lateinit var presenterCoroutine: DeliveringPresenterCoroutine
    private var listOrder: MutableList<Order> = mutableListOf()
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        presenterCoroutine.attachView(this)
        presenterCoroutine.getListOrder(3L)

    }

    override fun handleEvent() {
        binding.apply {
            tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> finish() // cho xac nhan
                        1 -> { // cho giao hang
                            val intent = Intent(this@DeliveringActivity, WattingDeliveryActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        2 -> {} // dang giao (current screen)
                    }
                }

                override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
                override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
            })
            
            // Set current tab to "dang giao" (position 2)
            tabLayout.selectTab(tabLayout.getTabAt(2))
            
            buttonSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val filteredList = filterOrderList(newText)
                    adapter.setData(filteredList)
                    return true
                }
            })
        }
    }

    private fun filterOrderList(query: String?): List<Order> {
        val filteredList = ArrayList<Order>()
        if (query.isNullOrBlank()) {
            filteredList.addAll(listOrder)
        } else {
            for (order in listOrder) {
                if (order.id.toString().contains(query, ignoreCase = true)) {
                    filteredList.add(order)
                }
            }
        }
        return filteredList
    }


    override fun onItemOrderShipperClick(id: Long) {
        val intent = Intent(this, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER, id)
        startActivity(intent)
    }

    override fun onItemReceiveOrderClick(id: Long) {

    }

    override fun onGetListOrderSuccess(list: List<Order>) {
        listOrder = list as MutableList<Order>
        adapter.setData(list)
    }


    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onGetListOrderFail() {
        listOrder.clear()
        adapter.clearData()
    }

    override fun onDestroy() {
        presenterCoroutine.onStop()
        super.onDestroy()
    }

}