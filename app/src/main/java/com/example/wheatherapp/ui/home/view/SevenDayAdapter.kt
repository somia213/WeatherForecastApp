package com.example.wheatherapp.ui.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherapp.data.models.ListResponse
import com.example.wheatherapp.databinding.DayTempCardBinding


 class SevenDayAdapter(var datalist:List<ListResponse>):RecyclerView.Adapter<SevenDayAdapter.UserHolder>() {
     class UserHolder(val binding: DayTempCardBinding) : RecyclerView.ViewHolder(binding.root) {
         fun bind(get: ListResponse) {
             binding.textCardDay.text = get.dtTxt
           //  binding.imageCardDayIcon = get.weather.get(0).icon
             binding.textCardDayTempDescription.text = get.weather?.get(0)?.description.toString()
             binding.textCardDayTemp.text = get.main?.temp.toString()

         }
     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
         val binding = DayTempCardBinding
             .inflate(LayoutInflater.from(parent.context), parent, false)
         return UserHolder(binding)
     }



     override fun onBindViewHolder(holder: UserHolder, position: Int) {
         holder.bind(datalist.get(position))
     }

     override fun getItemCount(): Int {
         return datalist.size
     }
 }