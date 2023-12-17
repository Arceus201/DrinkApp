package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.User
import com.example.drinkapp.databinding.ItemManagerUserBinding
import com.example.drinkapp.utils.listener.OnItemUserManagerClickListener

class RecyclerViewUserManagerAdapter(private val itemClickListener: OnItemUserManagerClickListener) :
    RecyclerView.Adapter<RecyclerViewUserManagerAdapter.ViewHolder?>() {
    private val listClient = mutableListOf<User>()

    fun setData(list: List<User>) {
        this.listClient.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun clearData() {
        listClient.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var viewBinding = ItemManagerUserBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listClient.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val client = listClient.get(position)
        holder.viewBinding.apply {
            textName.text = client.username
            textPhone.text = client.phone
            if (client.role == 1L) buttonStatus.setImageResource(R.drawable.ic_current_show)
            else buttonStatus.setImageResource(R.drawable.ic_current_hide)
            root.setOnClickListener {
                itemClickListener.onItemClick(client.id)
            }
            buttonStatus.setOnClickListener {
                itemClickListener.onItemStatusClick(client.id,client.role);
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemManagerUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }
}