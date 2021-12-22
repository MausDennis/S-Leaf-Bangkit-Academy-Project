package com.mnhyim.s_leaf.views.scan

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mnhyim.s_leaf.R
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.databinding.FragmentScanBinding
import com.mnhyim.s_leaf.views.detail.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

@FlowPreview
@ExperimentalCoroutinesApi
class ScanFragment : Fragment(), View.OnClickListener {

    private var TAG: String = this::class.java.simpleName

    private val scanViewModel: ScanViewModel by viewModel()

    private var _scanBinding: FragmentScanBinding? = null
    private val scanBinding get() = _scanBinding!!

    private val CAMERA_REQUEST: Int = 200
    private val GALLERY_REQUEST: Int = 300

    private lateinit var takenImage: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _scanBinding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return scanBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanBinding.imgScanResult.setImageResource(R.drawable.bg_rounded_all)
        scanBinding.btnCaptureImage.setOnClickListener(this)
        scanBinding.btnScanImage.setOnClickListener(this)
        scanBinding.btnGalleryPicker.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        scanBinding.progressBar.visibility = View.GONE
        scanViewModel.scanResult.removeObservers(viewLifecycleOwner)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_captureImage -> {
                captureImage()
            }
            R.id.btn_scanImage -> {
                scanBinding.progressBar.visibility = View.VISIBLE
                scanImage(takenImage)
            }
            R.id.btn_galleryPicker -> {
                pickGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST && data != null) {
            takenImage = data.extras?.get("data") as Bitmap
            scanBinding.imgScanResult.setImageBitmap(takenImage)
            scanBinding.btnScanImage.apply {
                isClickable = true
                isEnabled = true
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST && data != null) {
            val imageUri: Uri? = data.data
            val bitmap =
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
            takenImage = bitmap
            scanBinding.imgScanResult.setImageBitmap(bitmap)
            scanBinding.btnScanImage.apply {
                isClickable = true
                isEnabled = true
            }
        }
    }

    private fun captureImage() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    private fun pickGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
    }

    private fun scanImage(image: Bitmap) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val base64img = Base64.encodeToString(b, Base64.DEFAULT)

        scanViewModel.setScanImage(base64img)
        scanViewModel.scanResult.observe(viewLifecycleOwner, { plant ->
            openDialog(plant)
        })
    }

    private fun openDialog(plant: Plant) {
        val builder = AlertDialog.Builder(context)
        with (builder) {
            setTitle("Scan complete!")
            setMessage("Match level: ${plant.score}, Open Detail page?")
            setPositiveButton(
                "Open") { dialog, id ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_PLANT, plant)
                startActivity(intent)
            }
            setNegativeButton(
                "Cancel") { dialog, id ->
                // User cancelled the dialog
            }
            show()
        }
    }
}