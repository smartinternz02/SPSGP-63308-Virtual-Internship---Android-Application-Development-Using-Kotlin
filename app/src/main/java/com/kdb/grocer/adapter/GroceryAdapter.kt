package com.kdb.grocer.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kdb.grocer.R
import com.kdb.grocer.data.GroceryItem
import com.kdb.grocer.data.getFormattedPrice
import com.kdb.grocer.data.totalCost
import com.kdb.grocer.databinding.ItemGroceryBinding

class GroceryAdapter(private val onDeleteItem: (item: GroceryItem) -> Unit) :
    ListAdapter<GroceryItem, GroceryAdapter.GroceryItemViewHolder>(DiffCallback) {

    class GroceryItemViewHolder(
        private val binding: ItemGroceryBinding,
        private val onDeleteItem: (item: GroceryItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentItem: GroceryItem

        init {
            itemView.setOnCreateContextMenuListener { menu, view, contextMenuInfo ->
                menu.add(Menu.NONE, R.id.action_delete, Menu.NONE, R.string.action_delete)
                    .setOnMenuItemClickListener(deleteItemListener)
            }
        }

        private val deleteItemListener = MenuItem.OnMenuItemClickListener {
            onDeleteItem(currentItem)
            return@OnMenuItemClickListener true
        }

        fun bind(item: GroceryItem) {
            currentItem = item

            binding.apply {
                textViewName.text = item.name
                textViewTotalCost.text = item.getFormattedPrice(item.totalCost())

                with(root.context) {
                    textViewStockPrice.text = getString(
                        R.string.stock_price,
                        item.getFormattedPrice(),
                        item.quantity
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
        val binding =
            ItemGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GroceryItemViewHolder(binding, onDeleteItem)
    }

    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<GroceryItem>() {
        override fun areItemsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
            return oldItem == newItem
        }
    }
}