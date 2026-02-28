package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Address
import com.example.drinkapp.databinding.ItemAddressBinding
import com.example.drinkapp.utils.listener.OnItemAddressClickListener

class RecyclerViewAddressAdapter(
    private val itemClickListener: OnItemAddressClickListener,
    private val status: Int
) : ListAdapter<Address, RecyclerViewAddressAdapter.ViewHolder>(AddressDiffCallback()) {

    fun setData(list: List<Address>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemAddressBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = getItem(position)
        holder.viewBinding.apply {
            textName.text = address.name
            texttPhone.text = address.phone
            textAddress.text = address.address
            if (status == -1) {
                buttonChose.visibility = View.INVISIBLE
            } else {
                buttonChose.visibility = View.VISIBLE
            }
            buttonChose.setOnClickListener {
                itemClickListener.onItemChoseAddressClick(address)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemAddressBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class AddressDiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }
    }
}