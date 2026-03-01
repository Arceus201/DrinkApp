package com.example.drinkapp.screen.client.home

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.databinding.ClientActivitySearchBinding
import com.example.drinkapp.databinding.ClientFragmentHomeBinding
import com.example.drinkapp.screen.client.adapter.RecyclerViewDrinkClientAdapter
import com.example.drinkapp.screen.client.cart.CartActivity
import com.example.drinkapp.screen.client.drinkdetail.DrinkDetailActivity
import com.example.drinkapp.screen.client.ggmap.GgMapActivity
import com.example.drinkapp.screen.client.search.SearchActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.NextBackPage
import com.example.drinkapp.utils.base.BaseFragment
import com.example.drinkapp.utils.listener.OnItemDrinkClientClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment:BaseFragment<ClientFragmentHomeBinding>(ClientFragmentHomeBinding::inflate),
    HomeContract.View,
    OnItemDrinkClientClickListener{
    @Inject
    lateinit var presenterCoroutine: HomePresenterCoroutine
    private val adapter = RecyclerViewDrinkClientAdapter(this)
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        presenterCoroutine.attachView(this)
        presenterCoroutine.getProductHot()
    }

    override fun onResume() {
        super.onResume()
        presenterCoroutine.attachView(this)
        presenterCoroutine.getProductHot()
    }

    override fun handleEvent() {
        binding.apply {
            buttonSearch.setOnClickListener{
                context?.let { it1 -> NextBackPage(it1).startActivity(SearchActivity::class.java) }
            }
            buttonCart.setOnClickListener {
                context?.let { it1 -> NextBackPage(it1).startActivity(CartActivity::class.java) }
            }
        }
    }

    override fun onGetProductHotSuccess(list: List<Product>) {
        adapter.setData(list)
    }

    override fun onFail() {
        adapter.clearData()
    }

    override fun onItemDrinkClientClick(id: Long) {
        val intent = Intent(context, DrinkDetailActivity::class.java)
        intent.putExtra(Constant.KEY_PRODUCT_ID, id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        presenterCoroutine.onStop()
        super.onDestroyView()
    }
}