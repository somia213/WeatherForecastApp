package com.example.wheatherapp

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wheatherapp.databinding.ActivityMainBinding
import com.example.wheatherapp.local.FavouriteDataBase
import com.example.wheatherapp.local.FavouriteEntity
import com.example.wheatherapp.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

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


        val network  = RetrofitInstance().api
        val room = FavouriteDataBase.invoke(this)

        // Come From Retrofit
        lifecycleScope.launch(Dispatchers.Main) {
            // use async to wait when data came from network
            val weatherResponse = async{ network.getWeatherDetails(30.61554342119405, 32.27797547385768) }

            if (weatherResponse.await().isSuccessful){
                val data = weatherResponse.await().body()
//                Toast.makeText(this@MainActivity, data.toString(), Toast.LENGTH_SHORT).show()

                // Insert in DB


                val db = room.favouriteDao()
                withContext(Dispatchers.IO){
                    data?.let {
                        db.insertFavourites(FavouriteEntity(weather = data))
                    }
                }
                delay(3000)

                // retrieve from Database
                val favouritesResponse = async { room.favouriteDao().getFavourites() }
                Toast.makeText(this@MainActivity,"many of data in favourite table : ${favouritesResponse.await()}",Toast.LENGTH_SHORT).show()


                //delete favorite from database
//                db.deleteFavourite(favourite = favouritesResponse.await().get(0))
            }
        }
    }
}