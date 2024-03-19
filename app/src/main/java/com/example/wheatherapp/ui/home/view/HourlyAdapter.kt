import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheatherapp.data.models.ListResponse
import com.example.wheatherapp.databinding.TimeTempCardBinding
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter(private val dataList: List<ListResponse>) : RecyclerView.Adapter<HourlyAdapter.UserHolder>() {

    class UserHolder(val binding: TimeTempCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: ListResponse) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val date = inputFormat.parse(get.dtTxt)
            val formattedTime = outputFormat.format(date)

            binding.textCardTime.text = formattedTime

            Glide.with(binding.root.context)
                .load("https://openweathermap.org/img/wn/${get.weather?.get(0)?.icon}@2x.png")
                .into(binding.imageCardTempIcon)

            binding.textCardTemp.text = get.main?.temp.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = TimeTempCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}