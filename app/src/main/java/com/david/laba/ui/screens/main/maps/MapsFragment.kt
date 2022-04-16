package com.david.laba.ui.screens.main.maps

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.david.laba.R
import com.david.laba.application.App
import com.david.laba.auxiliary.AppSchedulers
import com.david.laba.databinding.FragmentMapsBinding
import com.david.laba.db.AppDatabase
import com.david.laba.db.entities.WeatherPoint
import com.david.laba.ext.dpToPx
import com.david.laba.ext.extend
import com.david.laba.ext.shrink
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.instance

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    companion object {
        fun getInstance(): MapsFragment = MapsFragment()
    }

    private val binding by viewBinding(FragmentMapsBinding::bind)

    private val room: AppDatabase by App.di.instance()
    private val appSchedulers: AppSchedulers by App.di.instance()

    private var googleMap: GoogleMap? = null

    private val baseLatLng = LatLng(53.893009, 27.567444)
    private val baseZoom = 16f
    private val initialZoom = 12f

    private var weatherPoints: ArrayList<WeatherPoint> = ArrayList()
    private var markers: ArrayList<Marker> = ArrayList()

    private val onMarkerClickListener = GoogleMap.OnMarkerClickListener { marker ->
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(marker.position, baseZoom),
            600,
            object : GoogleMap.CancelableCallback {
                override fun onFinish() {
                    displayUI(marker)
                }

                override fun onCancel() {
                }
            })

        true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.vGoogleMapsFragmentContainer) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

        initListeners()
    }

    private fun initListeners() {
        binding.inclWeatherInfo.vGlimpseBackground.setOnClickListener {
            hideUI()
        }
    }

    private fun getFacilitiesFromDb() {
        CoroutineScope(Dispatchers.IO).launch {
            weatherPoints = room.weatherDao().getAll() as ArrayList<WeatherPoint>

            withContext(Dispatchers.Main) {
                addFacilitiesToMap()
            }
        }
    }

    private fun addFacilitiesToMap() {
        weatherPoints.forEach { facility ->

            val markerOptions = MarkerOptions()
            markerOptions.position(LatLng(facility.latitude, facility.longitude))

            markerOptions.icon(
                getBitmapFromRes(R.drawable.ic_avatar_service_girl)
            )

            googleMap?.addMarker(markerOptions)?.let {
                markers.add(it)
            }
        }
    }

    private fun getBitmapFromRes(@DrawableRes res: Int): BitmapDescriptor {
        return BitmapDescriptorFactory.fromResource(
            res
        )
    }

    private fun showFacilityInfo(marker: Marker) {
        val weatherPoint = weatherPoints.find {
            it.latitude == marker.position.latitude && it.longitude == marker.position.longitude
        }

        binding.inclWeatherInfo.vTitle.text = weatherPoint?.title
        binding.inclWeatherInfo.vLatitude.text = weatherPoint?.latitude.toString()
        binding.inclWeatherInfo.vLongitude.text = weatherPoint?.longitude.toString()
        binding.inclWeatherInfo.vWeatherType.text = weatherPoint?.weatherType
    }

    private fun displayUI(marker: Marker) {
        showFacilityInfo(marker)

        showUI()

        binding.vBtnFacilityNext.setOnClickListener {
            hideUI()
            goNext(marker)
        }

        binding.vBtnFacilityPrev.setOnClickListener {
            hideUI()
            goPrev(marker)
        }
    }

    private fun showUI() {
        binding.vBtnFacilityNext.extend(50.dpToPx(), 50.dpToPx())
        binding.vBtnFacilityPrev.extend(50.dpToPx(), 50.dpToPx())

        binding.inclWeatherInfo.root.extend(binding.root.width, binding.root.height)
    }

    private fun hideUI() {
        binding.vBtnFacilityNext.shrink()
        binding.vBtnFacilityPrev.shrink()

        binding.inclWeatherInfo.root.shrink()
    }

    private fun goNext(marker: Marker) {
        var next = markers.indexOf(marker) + 1

        if (next >= markers.size) {
            next = 0
        }

        onMarkerClickListener.onMarkerClick(markers[next])
    }

    private fun goPrev(marker: Marker) {
        var prev = markers.indexOf(marker) - 1

        if (prev < 0) {
            prev = markers.lastIndex
        }

        onMarkerClickListener.onMarkerClick(markers[prev])
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(baseLatLng, initialZoom))

        initGoogleMapListeners()

        getFacilitiesFromDb()
    }

    private fun initGoogleMapListeners() {
        googleMap?.setOnCameraMoveStartedListener {
            //hideUI()
        }

        googleMap?.setOnMarkerClickListener(onMarkerClickListener)
    }
}