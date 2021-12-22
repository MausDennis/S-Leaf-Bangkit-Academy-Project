package com.mnhyim.s_leaf.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mnhyim.s_leaf.R
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.databinding.ListItemPlantBinding
import java.util.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val listHome = ArrayList<Plant>()
    var onItemClick: ((Plant) -> Unit)? = null

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemPlantBinding.bind(itemView)

        fun bind(data: Plant) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.imageURL[0])
                    .into(imgListItem)
                tvListItemName.text = data.name
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listHome[adapterPosition])
            }
        }
    }


    fun setData(newListData: List<Plant>?) {
        if (newListData == null) return
        listHome.clear()
        listHome.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_plant, parent, false)
        )

    override fun getItemCount() = listHome.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = listHome[position]
        holder.bind(data)
    }
}