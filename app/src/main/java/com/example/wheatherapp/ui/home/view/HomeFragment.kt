package com.example.wheatherapp.ui.home.view

import Repository
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapp.R
import com.example.wheatherapp.data.HomeResponseState
import com.example.wheatherapp.data.local.cash.getSettingsUserLocation
import com.example.wheatherapp.data.models.City
import com.example.wheatherapp.data.models.WeatherResponse
import com.example.wheatherapp.databinding.FragmentHomeBinding
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModel
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    // view binding of home xml
    private lateinit var binding: FragmentHomeBinding


    lateinit var viewModel : HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

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
                        Toast.makeText(requireContext(),state.data.toString(),Toast.LENGTH_SHORT).show()
                        binding.textCity.text = state.data.city?.name
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