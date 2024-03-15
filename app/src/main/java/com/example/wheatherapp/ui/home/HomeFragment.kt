package com.example.wheatherapp.ui.home

import Repository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.wheatherapp.R
import com.example.wheatherapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var viewModel : HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository(requireContext())
        val viewModelFactory = HomeViewModelFactory(repository)
        //The ViewModelProvider used to reuse the first created ViewModel instance if it doesn’t change (which is often the case).
        //you ensure that your ViewModel is scoped correctly to the Lifecycle of the associated component
        viewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(HomeViewModel::class.java)

        viewModel.getWeatherDetails(30.61554342119405, 32.27797547385768)

        viewModel.weatherDetails.observe(viewLifecycleOwner){weather ->
            Toast.makeText(requireContext(),weather.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}