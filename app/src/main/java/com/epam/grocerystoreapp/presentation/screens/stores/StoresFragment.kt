package com.epam.grocerystoreapp.presentation.screens.stores

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentStoresBinding
import com.epam.grocerystoreapp.presentation.utils.CheckPermissionHelper
import com.epam.grocerystoreapp.presentation.utils.Const.STORE_ID_KEY
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoresFragment : Fragment(R.layout.fragment_stores), OnMapReadyCallback {

    private lateinit var binding: FragmentStoresBinding
    private val viewModel: StoresViewModel by viewModels()

    private var googleMap: GoogleMap? = null

    private var mapsPermissionHelper: CheckPermissionHelper? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoresBinding.bind(view)

        mapsPermissionHelper = CheckPermissionHelper(
            this,
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) {
            initLocation()
        }

        initObservers()
        launchMap()
        initToolbar()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        mapsPermissionHelper?.checkPermission()
        moveCamera(googleMap, LAT_LNG_DEFAULT)
        initMapMarkers(googleMap)
    }

    private fun launchMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this@StoresFragment)
    }

    private fun initObservers() {
        viewModel.storeId().observe(viewLifecycleOwner) { storeId ->
            val args = bundleOf(STORE_ID_KEY to storeId)
            findNavController().navigate(R.id.action_storesFragment_to_storeFragment, args)
        }

        viewModel.toastResId().observe(viewLifecycleOwner) { resId ->
            Toast.makeText(
                requireContext(),
                getString(resId),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun moveCamera(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                ZOOM_SCALE
            )
        )
    }

    private fun initMapMarkers(googleMap: GoogleMap) {
        viewModel.stores.observe(viewLifecycleOwner) { stores ->
            val markerIcon = bitmapDescriptorFromVector(
                requireContext(),
                R.drawable.ic_store_marker
            )

            stores.forEach { store ->
                val latLng = LatLng(store.latitude, store.longitude)

                val marker = MarkerOptions()
                    .position(latLng)
                    .icon(markerIcon)

                googleMap.addMarker(marker)
            }
        }

        googleMap.setOnMarkerClickListener { marker ->
            viewModel.onMarkerClicked(marker)
            true
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocation() {
        googleMap?.let { map ->
            map.isMyLocationEnabled = true

            val token = CancellationTokenSource().token

            LocationServices
                .getFusedLocationProviderClient(requireContext())
                .getCurrentLocation(
                    LocationRequest.PRIORITY_HIGH_ACCURACY,
                    token
                )
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        moveCamera(map, latLng)
                    }
                }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                intrinsicWidth,
                intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun initToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.stores_frag_title)
            setDisplayHomeAsUpEnabled(false)
            show()
        }
    }

    companion object {
        private const val ZOOM_SCALE = 12f
        private val LAT_LNG_DEFAULT = LatLng(56.326140, 44.001764)
    }

}
