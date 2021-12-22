package com.mnhyim.s_leaf.views.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.ui.HomeAdapter
import com.mnhyim.s_leaf.databinding.FragmentHomeBinding
import com.mnhyim.s_leaf.utils.Constants.CAROUSEL_AUTOPLAY
import com.mnhyim.s_leaf.utils.DataMapper
import com.mnhyim.s_leaf.views.detail.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class HomeFragment : Fragment() {

    private var TAG: String = this::class.java.simpleName

    private val homeViewModel: HomeViewModel by viewModel()

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val homeAdapter = HomeAdapter()
            homeAdapter.onItemClick = { selectedData ->
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

            with(homeBinding.rvPlants) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = homeAdapter
            }

            homeViewModel.listPlants.observe(viewLifecycleOwner, { data ->
                homeAdapter.setData(data)
                if (homeAdapter.getItemCount() > 0) homeBinding.progressBar.visibility =
                    View.INVISIBLE
            })

            setCarousel()
        }
    }

    // Setting Carousel
    private fun setCarousel() {
        homeViewModel.getPlant().observe(viewLifecycleOwner, { data ->
            val datax = DataMapper.mapDomainToCarouselItem(data)
            homeBinding.csHome.apply {
                registerLifecycle(lifecycle)
                autoPlay = true
                autoPlayDelay = CAROUSEL_AUTOPLAY
                setData(datax)
                touchToPause = true
                infiniteCarousel = true
                carouselListener = object : CarouselListener {
                    override fun onClick(position: Int, carouselItem: CarouselItem) {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_PLANT, data[position])
                        startActivity(intent)
                    }
                }
            }
        })
    }
}