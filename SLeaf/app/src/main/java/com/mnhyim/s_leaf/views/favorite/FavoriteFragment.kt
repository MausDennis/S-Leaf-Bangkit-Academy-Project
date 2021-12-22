package com.mnhyim.s_leaf.views.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.ui.FavoriteAdapter
import com.mnhyim.s_leaf.databinding.FragmentFavoriteBinding
import com.mnhyim.s_leaf.views.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var TAG: String = this::class.java.simpleName

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _favoriteBinding: FragmentFavoriteBinding? = null
    private val favoriteBinding get() = _favoriteBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _favoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val favoriteAdapter = FavoriteAdapter()
            favoriteAdapter.onItemClick = { selectedData ->
                val plant = Plant(
                    id = selectedData.id,
                    className = selectedData.className,
                    name = selectedData.name,
                    desc = selectedData.desc,
                    scientificName = selectedData.scientificName,
                    imageURL = listOf(selectedData.imageURL[0], selectedData.imageURL[1]),
                    isFavorite = true
                )
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_PLANT, plant)
                startActivity(intent)
            }

            favoriteViewModel.listFavorite.observe(viewLifecycleOwner, { dataFavorite ->
                favoriteAdapter.setData(dataFavorite)
                if (dataFavorite.size > 0) {
                    favoriteBinding.tvEmptyFavorites.visibility = View.GONE
                } else {
                    favoriteBinding.tvEmptyFavorites.visibility = View.VISIBLE
                }
            })

            with(favoriteBinding.rvPlants) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteAdapter
            }
        }
    }
}