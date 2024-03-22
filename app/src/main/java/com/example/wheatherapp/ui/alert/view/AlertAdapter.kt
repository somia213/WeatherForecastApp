package com.example.wheatherapp.ui.alert.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherapp.data.models.AlertModel
import com.example.wheatherapp.databinding.AlertCardBinding
import com.example.wheatherapp.dayConverterToString
import com.example.wheatherapp.timeConverterToString

class AlertAdapter(val context: Context , val onDeleteClicked:(model:AlertModel)->Unit):
    RecyclerView.Adapter<AlertAdapter.FavoriteCityViewHolder>() {

    inner class FavoriteCityViewHolder(val itembinding : AlertCardBinding):
            RecyclerView.ViewHolder(itembinding.root)

    private val differCallback = object :DiffUtil.ItemCallback<AlertModel>(){
        override fun areItemsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this , differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlertAdapter.FavoriteCityViewHolder {
        val itembinding = AlertCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteCityViewHolder(itembinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: FavoriteCityViewHolder
        , position: Int) {
        val alertModel = differ.currentList[position]

        // convert timeStap to human readable data and time
        holder.itembinding.textFrom.text = "${dayConverterToString(alertModel.startDate?:0,context)}  ${timeConverterToString(alertModel.startTime?:0,context)}"
        holder.itembinding.textTo.text = "${dayConverterToString(alertModel.endDate?:0,context)}  ${timeConverterToString(alertModel.endTime?:0,context)}"

        // delete action
        holder.itembinding.btnDelete.setOnClickListener{
            onDeleteClicked(alertModel)
        }
    }

}
