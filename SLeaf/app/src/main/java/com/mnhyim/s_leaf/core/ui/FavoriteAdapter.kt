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

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<Plant>()
    var onItemClick: ((Plant) -> Unit)? = null

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                onItemClick?.invoke(listFavorite[adapterPosition])
            }
        }
    }


    fun setData(newListData: List<Plant>?) {
        if (newListData == null) return
        listFavorite.clear()
        listFavorite.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_plant, parent, false)
        )

    override fun getItemCount() = listFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val data = listFavorite[position]
        holder.bind(data)
    }
}