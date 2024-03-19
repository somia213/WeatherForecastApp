package com.example.wheatherapp.ui.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherapp.data.models.ListResponse
import com.example.wheatherapp.databinding.TimeTempCardBinding

class HourlyAdapter(var datalist:List<ListResponse>): RecyclerView.Adapter<HourlyAdapter.UserHolder>() {

    class UserHolder(val binding: TimeTempCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: ListResponse) {
            binding.textCardTime.text = get.dtTxt
        //    binding.imageCardTempIcon
              binding.textCardTemp.text = get.main?.temp.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = TimeTempCardBinding
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




