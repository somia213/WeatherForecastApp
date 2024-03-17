package com.example.wheatherapp.ui.map

import Repository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherapp.R
import com.example.wheatherapp.data.models.WeatherResponse
import com.example.wheatherapp.databinding.FragmentHomeBinding
import com.example.wheatherapp.databinding.FragmentLocationBinding
import com.example.wheatherapp.databinding.FragmentMapBinding
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModel
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModelFactory

class MapFragment : Fragment() {

    // view binding of home xml
    private lateinit var binding:FragmentMapBinding


    lateinit var viewModel : HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}