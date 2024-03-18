package com.example.wheatherapp.ui.favourite

import Repository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapp.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {
    private lateinit var viewModel:FavouriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository(requireContext())
        val viewModelFactory = FavouriteViewModelFactory(repository = repository)

        viewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(FavouriteViewModel::class.java)

        viewModel .getFavouriteList()
        lifecycleScope.launch {
            viewModel.favouriteList.collect{favouriteList ->
                Toast.makeText(requireContext(),"length of data is :${favouriteList.size} ",Toast.LENGTH_SHORT).show()
            }
        }
    }
}