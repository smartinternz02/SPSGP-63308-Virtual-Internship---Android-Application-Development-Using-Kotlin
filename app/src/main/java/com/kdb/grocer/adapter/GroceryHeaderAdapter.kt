package com.kdb.grocer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kdb.grocer.R

class GroceryHeaderAdapter : RecyclerView.Adapter<GroceryHeaderAdapter.HeaderViewHolder>() {

    class HeaderViewHolder(root: View) : RecyclerView.ViewHolder(root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) { }
    override fun getItemCount(): Int = 1
}