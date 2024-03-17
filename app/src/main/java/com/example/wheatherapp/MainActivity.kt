package com.example.wheatherapp

import Repository
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.LocaleManagerCompat
import androidx.core.location.LocationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wheatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.security.Permission
const val PERMISSION_REQUEST_CODE = 55
class MainActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // Testing Using Repository

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // we can take location by fusedLocationProvider --> used gms-location services library (gradle)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // Check Location
        if (checkPermission()) {
            if (isEnabledLocation()) {
              //  Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                getLastLocation()
              }else{
                Toast.makeText(this, "you should enabled location", Toast.LENGTH_SHORT).show()
            }
        }
              //  Toast.makeText(this,"Permission Not granted",Toast.LENGTH_SHORT).show()
                     requestPermission()

    }


    // Correct method after revision
    fun checkPermission(): Boolean {
        val fineLocation = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseLocation = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fineLocation && coarseLocation
    }

    // Correct method after revision
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, listOf(
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
                this,
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
                Toast.makeText(
                    this,
                    "${location.latitude} and ${location.longitude}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    fun isEnabledLocation():Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }
}