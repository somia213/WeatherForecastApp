package com.example.wheatherapp.ui.location

import Repository
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wheatherapp.PERMISSION_REQUEST_CODE
import com.example.wheatherapp.R
import com.example.wheatherapp.data.local.cash.setSharedLocation
import com.example.wheatherapp.data.models.WeatherResponse
import com.example.wheatherapp.databinding.FragmentHomeBinding
import com.example.wheatherapp.databinding.FragmentLocationBinding
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModel
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

class LocationFragment : Fragment() {

    // view binding of home xml
    private lateinit var binding: FragmentLocationBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    lateinit var viewModel : HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // we can take location by fusedLocationProvider --> used gms-location services library (gradle)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.currentLocationBtn.setOnClickListener{


//            val requestMultiplePermissions = registerForActivityResult(
//                ActivityResultContracts.RequestMultiplePermissions()
//            ) { permissions ->
//                permissions.entries.forEach {
//                    Log.d("DEBUG", "${it.key} = ${it.value}")
//                    getLastLocation()
//                }
//            }
//
//            requestMultiplePermissions.launch(
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                )
//            )

            // Check Location
            if (checkPermission()) {
                if (isEnabledLocation()) {
                    //  Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                    getLastLocation()
                }else{
                    Toast.makeText(requireContext(), "you should enabled location", Toast.LENGTH_SHORT).show()
                }
            }
            //  Toast.makeText(this,"Permission Not granted",Toast.LENGTH_SHORT).show()
            requestPermission()
        }
        binding.mapBtn.setOnClickListener{
            findNavController().navigate(R.id.mapFragment)
        }

    }


    // Correct method after revision
    fun checkPermission(): Boolean {
        val fineLocation = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseLocation = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fineLocation && coarseLocation
    }

    // Correct method after revision
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
            ).toTypedArray()
            , PERMISSION_REQUEST_CODE
        )
    }

    // Correct method after revision

    // Generated override methods
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            // Toast.makeText(this,"we can access your location",Toast.LENGTH_SHORT).show()
                getLastLocation()
        } else {
            Toast.makeText(
                requireContext(),
                "Sorry you should accept permission to use App",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        fusedLocationProviderClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                // or by navArgs
                findNavController().navigate(R.id.navigation_home)
                requireContext().setSharedLocation(LatLng(location.latitude,location.longitude))
                Toast.makeText(
                    requireContext(),
                    "${location.latitude} and ${location.longitude}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    fun isEnabledLocation():Boolean{
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }



}