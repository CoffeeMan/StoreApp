package com.epam.grocerystoreapp.presentation.screens.scan

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentScanBinding
import com.epam.grocerystoreapp.domain.model.CatalogItem
import com.epam.grocerystoreapp.presentation.utils.CheckPermissionHelper
import com.epam.grocerystoreapp.presentation.utils.Const
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : Fragment(R.layout.fragment_scan) {

    private lateinit var binding: FragmentScanBinding

    private val viewModel: ScanViewModel by viewModels()

    private val barcodeCallback = BarcodeCallback { barcodeResult ->
        viewModel.getProductByBarcode(barcodeResult.text)
        Toast.makeText(requireActivity(), "Scanned ${barcodeResult.text}", Toast.LENGTH_SHORT).show()
        binding.barcodeView.pause()
    }

    private var requestManager: RequestManager? = null

    private val cameraPermissionHelper by lazy {
        CheckPermissionHelper(
            this,
            requireActivity(),
            Manifest.permission.CAMERA,
        ) { binding.barcodeView.resume() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScanBinding.bind(view)
        requestManager = Glide.with(requireActivity())
        initViews()
        initBarcodeScanner()
        initObservers()
        initToolbar()
        cameraPermissionHelper.checkPermission()
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeView.pause()
    }

    private fun initViews() {
        binding.scanItemGifImageView.setImageResource(R.drawable.gif_scan_product)
        binding.addProductButton.visibility = View.GONE
        binding.productPreview.visibility = View.GONE
        binding.addProductButton.setOnClickListener {
            viewModel.addScannedProductToCart()
        }
    }

    private fun initObservers() {
        viewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->
            productInfo?.let { productInfoValue ->
                binding.scanItemGifImageView.visibility = View.GONE
                binding.addProductButton.visibility = View.VISIBLE
                binding.productPreview.visibility = View.VISIBLE
                binding.barcodeView.resume()
                setUpUIForProduct(productInfoValue)
            }
        }
    }

    private fun setUpUIForProduct(item: CatalogItem) {
        binding.productTitle.text = item.name
        binding.productPrice.text = item.price.toString()
        binding.productValue.text = item.unit.name
        binding.productDescription.text = item.description
        try {
            requestManager?.let { manager ->
                manager.load(item.photoUrl)
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.no_photo)
                    .into(binding.productPreview)
            }
        } catch (e: Exception) {
            Log.e(Const.APP_LOG_TAG, e.message.toString())
            binding.productPreview.setImageResource(R.drawable.no_photo)
        }
    }

    private fun initBarcodeScanner() {
        binding.barcodeView.decoderFactory = DefaultDecoderFactory(scannerBarcodeFormats)
        binding.barcodeView.barcodeView.cameraSettings.run {
            requestedCameraId = BACK_CAMERA_ID
            isAutoFocusEnabled = true
        }
        binding.barcodeView.setStatusText(null)
        binding.barcodeView.decodeContinuous(barcodeCallback)
    }

    private fun initToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.scan_frag_title)
            setDisplayHomeAsUpEnabled(false)
            show()
        }
    }

    companion object {
        private const val BACK_CAMERA_ID = 0
        private val scannerBarcodeFormats: Collection<BarcodeFormat> =
            listOf(
                BarcodeFormat.UPC_E,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.CODE_128,
                BarcodeFormat.QR_CODE,
                BarcodeFormat.CODE_39
            )
    }

}
