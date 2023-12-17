package com.example.drinkapp.screen.client.search

import android.content.Intent
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.call.CallApiDrink
import com.example.drinkapp.databinding.ClientActivitySearchBinding
import com.example.drinkapp.screen.client.adapter.RecyclerViewDrinkClientAdapter
import com.example.drinkapp.screen.client.drinkdetail.DrinkDetailActivity
import com.example.drinkapp.screen.client.home.HomeFragment
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.NextBackPage
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.listener.OnItemDrinkClientClickListener

class SearchActivity : BaseActivity<ClientActivitySearchBinding>(ClientActivitySearchBinding::inflate),
SearchContract.View, OnItemDrinkClientClickListener {
    private lateinit var presenter: SearchPresenter
    private val adapter = RecyclerViewDrinkClientAdapter(this)
    private lateinit var listP : List<Product>
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        presenter = SearchPresenter(this, CallApiDrink.getInstance())
        presenter?.getAllProduct()
    }

    override fun onResume() {
        super.onResume()
        presenter = SearchPresenter(this, CallApiDrink.getInstance())
        presenter?.getAllProduct()
    }

    override fun handleEvent() {
       binding.apply {
           buttonSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(query: String?): Boolean {
                   return true
               }

               override fun onQueryTextChange(newText: String?): Boolean {
                   val filteredList = filterProductList(newText)
                   adapter.setData(filteredList)
                   return true
               }
           })
           buttonBack.setOnClickListener {
//               NextBackPage(applicationContext).startActivity(HomeFragment::class.java)
               finish()
           }
       }
    }

    private fun filterProductList(query: String?): List<Product> {
        val filteredList = ArrayList<Product>()
        if (query.isNullOrBlank()) {
            filteredList.addAll(listP)
        } else {
            for (product in listP) {
                if (product.name.contains(query, ignoreCase = true)) {
                    filteredList.add(product)
                }
            }
        }
        return filteredList
    }
    override fun onGetAllProductSuccess(list: List<Product>) {
        listP = list
        adapter.setData(list)
    }

    override fun onFail() {
        adapter.clearData()
    }

    override fun onItemDrinkClientClick(id: Long) {
        val intent = Intent(this, DrinkDetailActivity::class.java)
        intent.putExtra(Constant.KEY_PRODUCT_ID, id)
        startActivity(intent)
    }
}