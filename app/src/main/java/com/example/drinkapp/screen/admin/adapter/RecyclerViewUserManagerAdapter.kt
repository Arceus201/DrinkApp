package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.User
import com.example.drinkapp.databinding.ItemManagerUserBinding
import com.example.drinkapp.utils.listener.OnItemUserManagerClickListener

class RecyclerViewUserManagerAdapter(private val itemClickListener: OnItemUserManagerClickListener) :
    ListAdapter<User, RecyclerViewUserManagerAdapter.ViewHolder>(UserDiffCallback()) {

    fun setData(list: List<User>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemManagerUserBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val client = getItem(position)
        holder.viewBinding.apply {
            textName.text = client.username
            textPhone.text = client.phone
            if (client.role == 1L) {
                buttonStatus.setImageResource(R.drawable.ic_current_show)
            } else {
                buttonStatus.setImageResource(R.drawable.ic_current_hide)
            }
            root.setOnClickListener {
                itemClickListener.onItemClick(client.id)
            }
            buttonStatus.setOnClickListener {
                itemClickListener.onItemStatusClick(client.id, client.role)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemManagerUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}