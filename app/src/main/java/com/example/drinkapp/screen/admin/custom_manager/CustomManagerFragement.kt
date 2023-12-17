package com.example.drinkapp.screen.admin.custom_manager

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.databinding.AdminFragmentCustomManagerBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewUserManagerAdapter
import com.example.drinkapp.screen.admin.custom_manager_order.CustomManagerOrderActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseFragment
import com.example.drinkapp.utils.listener.OnItemUserManagerClickListener

class CustomManagerFragement :
    BaseFragment<AdminFragmentCustomManagerBinding>(AdminFragmentCustomManagerBinding::inflate),
    CustomManagerContract.View,
    OnItemUserManagerClickListener {
    private lateinit var presenter: CustomManagerPresenter
    private val adapter = RecyclerViewUserManagerAdapter(this)
    private var listCustomManager: MutableList<User> = mutableListOf()
    override fun initView() {
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        presenter = CustomManagerPresenter(this, CallApiUser.getInstance())
        presenter.getAllClient()
    }

    override fun handleEvent() {
        binding.apply {
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

    private fun filterOrderList(query: String?): List<User> {
        val filteredList = ArrayList<User>()
        if (query.isNullOrBlank()) {
            filteredList.addAll(listCustomManager)
        } else {
            for (user in listCustomManager) {
                if (user.phone.contains(query, ignoreCase = true)) {
                    filteredList.add(user)
                }
            }
        }
        return filteredList
    }

    override fun onGetAllClientSuccess(list: List<User>) {
        listCustomManager = list as MutableList<User>
        adapter.setData(list)
    }

    override fun onUpdateClientStatusSuccess(list: List<User>) {
        listCustomManager = list as MutableList<User>
        adapter.setData(list)
    }

    override fun onGetAllClientFail() {
        listCustomManager.clear()
        adapter.clearData()
    }

    override fun onFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(id: Long) {
        val intent = Intent(context, CustomManagerOrderActivity::class.java)
        intent.putExtra(Constant.KEY_ORDER_MANAGER, id)
        startActivity(intent)
    }

    override fun onItemStatusClick(id: Long, current_status: Long) {
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle(UPDATE_STATUS_TITLE)
        alertDialog.setMessage(UPDATE_STATUS_MESSAGGE)
        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            val newStatus = current_status*-1L
            presenter.updateClientStatus(id,newStatus)
        }
        alertDialog.setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    companion object{
        const val UPDATE_STATUS_TITLE = "Xác nhận thay đổi trạng thái"
        const val UPDATE_STATUS_MESSAGGE = "Bạn có chắc muốn xác nhận thay đổi trạng thái tài khoản khách hàng?"
    }
}