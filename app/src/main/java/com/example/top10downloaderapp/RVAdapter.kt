package com.example.top10downloaderapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.top10downloaderapp.databinding.ActivityTop10Binding
import java.util.ArrayList


class RVAdapter(private val appList : ArrayList<topData>):RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    class ItemViewHolder(val Binding: ActivityTop10Binding) : RecyclerView.ViewHolder(Binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ActivityTop10Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.Binding.apply {
            top.text = appList[position].name
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}