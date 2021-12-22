package com.mnhyim.s_leaf.views.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mnhyim.s_leaf.R
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.databinding.ActivityDetailBinding
import com.mnhyim.s_leaf.utils.Constants
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private var TAG: String = this::class.java.simpleName
    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    companion object {
        const val EXTRA_PLANT = "extra_plant"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val plant = intent.getParcelableExtra<Plant>(EXTRA_PLANT) as Plant
        detailViewModel.checkFavorite(plant.name).observe(this, { data ->
            if (data > 0) {
                detailBinding.btnAddFavorite.text = getText(R.string.title_remove_favorite)
                detailBinding.btnAddFavorite.setOnClickListener {
                    detailViewModel.removeFavorite(plant.name)
                    Toast.makeText(this, "${plant.name} removed from favorite!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                detailBinding.btnAddFavorite.text = getText(R.string.title_add_favorite)
                detailBinding.btnAddFavorite.setOnClickListener {
                    detailViewModel.addFavorite(plant)
                    Toast.makeText(this, "${plant.name} added to favorite!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        setUpView(plant)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpView(plant: Plant) {
        val carouselData = listOf<CarouselItem>(
            CarouselItem(
                imageUrl = plant.imageURL[0]
            ),
            CarouselItem(
                imageUrl = plant.imageURL[1]
            )
        )

        title = plant.scientificName

        with(detailBinding) {
            csDetail.apply {
                registerLifecycle(lifecycle)
                autoPlay = true
                setData(carouselData)
                autoPlayDelay = Constants.CAROUSEL_AUTOPLAY
                touchToPause = true
                infiniteCarousel = true
                showNavigationButtons = false
            }

            tvDetailName.text = plant.name
            tvDetailScientificName.text = plant.scientificName
            tvDetailDesc.text = plant.desc
        }
    }
}