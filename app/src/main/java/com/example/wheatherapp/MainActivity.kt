package com.example.wheatherapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wheatherapp.databinding.ActivityMainBinding
const val PERMISSION_REQUEST_CODE = 55
class MainActivity : AppCompatActivity() ,NavController.OnDestinationChangedListener {



    // Testing Using Repository

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView



        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // listen to any change in nav graph
        navController.addOnDestinationChangedListener(this)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//*************************************************************
    }
   // save cast -> as?
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when(destination.id){
            R.id.locationFragment , R.id.mapFragment -> {
                supportActionBar?.hide()
                navView.visibility= View.GONE
            }
            else->{
                supportActionBar?.show()
                navView.visibility= View.VISIBLE
            }
        }
    }

}