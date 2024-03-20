package com.example.wheatherapp.ui.map.view

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherapp.R
import com.example.wheatherapp.databinding.FragmentMapBinding
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModel
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MapFragment : Fragment(), OnMapReadyCallback {

    // View binding of home XML
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var viewModel: HomeViewModel

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

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Places.initialize(requireContext(), getString(R.string.Map))
        placesClient = Places.createClient(requireContext())

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS))

        //  Alert ************************/
        fun checkSaveToFavourite(){
            val alert:AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            alert.setTitle("favourite")

            alert.setMessage("Do you want to save this place on favourite")
            alert.setPositiveButton("save"){_:DialogInterface,_:Int->
            Toast.makeText(requireContext(),"data has been saved",Toast.LENGTH_SHORT).show()
            }
            alert.setNegativeButton("no"){_:DialogInterface,_:Int ->
               // dialog.dismiss()
            }
            val dialog = alert.create()
            dialog.show()
        }

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                place.latLng?.let {
                    map.clear()
                    map.addMarker(
                        MarkerOptions()
                            .position(it)
                            .title(place.name)
                    )
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))
//                    map.setOnMarkerClickListener {
////                        // save long and lat in shared prefrence
////                        // and send to home
//                    }

                    map.setOnMapLongClickListener {  }

                    // Update home XML with the selected place
                 //   viewModel.updateSelectedPlace(place)
                }
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}