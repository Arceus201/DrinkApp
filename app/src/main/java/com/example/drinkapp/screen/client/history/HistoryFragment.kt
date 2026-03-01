package com.example.drinkapp.screen.client.history

import android.content.Intent
import android.widget.Toast
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.databinding.ClientFragmentHistoryBinding
import com.example.drinkapp.databinding.ClientFragmentHomeBinding
import com.example.drinkapp.screen.client.adapter.RecyclerViewOrderAdapter
import com.example.drinkapp.screen.client.order_detail.OrderDetailActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseFragment
import com.example.drinkapp.utils.listener.OnItemOrderClientClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<ClientFragmentHistoryBinding>(ClientFragmentHistoryBinding::inflate),
    HistoryContract.View,
    OnItemOrderClientClickListener {
    @Inject
    lateinit var presenter: HistoryPresenterCoroutine
    private val adapter = RecyclerViewOrderAdapter(this)
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        val user = context?.let { UserManager.getUserInfo(it) }
        presenter.attachView(this)
        if (user != null) {
            user.id?.let { presenter.getHistoryOrder(it) }
        }
    }

    override fun handleEvent() {
        //TODO("Not yet implemented")
    }

    override fun onGetAllHistoryOrderSuccess(list: List<Order>) {
        adapter.setData(list)
    }

    override fun onGetAllHistoryOrderFail() {
        adapter.clearData()
    }

    override fun onFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    override fun onItemOrderClientClick(id: Long) {
        val intent = Intent(context, OrderDetailActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER, id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val user = context?.let { UserManager.getUserInfo(it) }
        presenter.attachView(this)
        if (user != null) {
            user.id?.let { presenter.getHistoryOrder(it) }
        }
    }

    override fun onDestroyView() {
        presenter.onStop()
        super.onDestroyView()
    }
}