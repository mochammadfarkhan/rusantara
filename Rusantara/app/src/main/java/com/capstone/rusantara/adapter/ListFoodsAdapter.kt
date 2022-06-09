package com.capstone.rusantara.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.rusantara.databinding.FoodRowBinding
import com.capstone.rusantara.models.ImageData

class ListFoodsAdapter(private val listFoods: List<ImageData>) : RecyclerView.Adapter<ListFoodsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FoodRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (img, name, info) = listFoods[position]

        Glide.with(holder.itemView.context)
            .load(img)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)
        holder.binding.foodName.text = name
        holder.binding.foodOrigin.text = info

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFoods[position])
        }
    }

    override fun getItemCount(): Int = listFoods.size

    class ListViewHolder(var binding: FoodRowBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ImageData)
    }
}