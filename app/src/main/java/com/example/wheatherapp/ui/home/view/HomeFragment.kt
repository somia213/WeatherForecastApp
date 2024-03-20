package com.example.wheatherapp.ui.home.view

import HourlyAdapter
import Repository
import SevenDayAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wheatherapp.data.HomeResponseState
import com.example.wheatherapp.data.local.cash.getSettingsUserLocation
import com.example.wheatherapp.databinding.FragmentHomeBinding
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModel
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {

    // view binding of home xml
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterDaily: SevenDayAdapter
    private lateinit var adapterHourly: HourlyAdapter


    lateinit var viewModel : HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository(requireContext())
        val viewModelFactory = HomeViewModelFactory(repository)
        //The ViewModelProvider used to reuse the first created ViewModel instance if it doesn’t change (which is often the case).
        //you ensure that your ViewModel is scoped correctly to the Lifecycle of the associated component
        viewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(HomeViewModel::class.java)

        val latLang = requireContext().getSettingsUserLocation()
        viewModel.getWeatherDetails(latLang.latitude,latLang.longitude)

        lifecycleScope.launch {
            viewModel.weatherDetails.collect{state ->
                when(state){
                    is HomeResponseState.OnSuccess->{
                    //    Toast.makeText(requireContext(),state.data.toString(),Toast.LENGTH_SHORT).show()
                        binding.textCity.text = state.data.city?.name

                        binding.textCurrentDay.text = state.data.list.get(0).let {
                            SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(
                                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(it.dtTxt)
                            )
                        }
                        binding.textCurrentDate

                        Glide.with(this@HomeFragment)
                            .load("https://openweathermap.org/img/wn/${state.data.list.get(0).weather?.get(0)?.icon}@2x.png")
                            .into(binding.imageWeatherIcon)

                        binding.textCurrentTempreture.text = state.data.list.get(0).main?.temp.toString()
                        binding.textTempDescription.text = state.data.list.get(0).weather?.get(0)?.description
                        binding.textPressure.text = state.data.list.get(0).main?.pressure.toString()
                        binding.textClouds.text=state.data.list.get(0).clouds.toString()
                        binding.textWindSpeed.text = state.data.list.get(0).wind?.speed.toString()
                        binding.textVisibility.text = state.data.list.get(0).visibility.toString()
                        binding.textHumidity.text = state.data.list.get(0).main?.humidity.toString()

                        // Day Adapter
                        adapterDaily = SevenDayAdapter(state.data.list)
                        binding.recyclerViewTempPerDay.adapter = adapterDaily
                       // binding.recyclerViewTempPerDay.layoutManager = LinearLayoutManager(requireContext())

                        // Hour Adapter
                        adapterHourly = HourlyAdapter(state.data.list)
                        binding.recyclerViewTempPerTime.adapter=adapterHourly
                       // binding.recyclerViewTempPerTime.layoutManager = LinearLayoutManager(requireContext())

                    }
                    is HomeResponseState.OnError->{
                        Toast.makeText(requireContext(),state.message,Toast.LENGTH_SHORT).show()
                    }
                    is HomeResponseState.OnLoading->{
                        Toast.makeText(requireContext(),"Loading........",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }




}