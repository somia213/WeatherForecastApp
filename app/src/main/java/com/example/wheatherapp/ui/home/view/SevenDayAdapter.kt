import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheatherapp.data.models.ListResponse
import com.example.wheatherapp.databinding.DayTempCardBinding
import java.text.SimpleDateFormat
import java.util.*

class SevenDayAdapter(private val dataList: List<ListResponse>) : RecyclerView.Adapter<SevenDayAdapter.UserHolder>() {

    class UserHolder(val binding: DayTempCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: ListResponse) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            val date = inputFormat.parse(get.dtTxt)
            val formattedDay = outputFormat.format(date)

            binding.textCardDay.text = formattedDay

            Glide.with(binding.root.context)
                .load("https://openweathermap.org/img/wn/${get.weather?.get(0)?.icon}@2x.png")
                .into(binding.imageCardDayIcon)

            binding.textCardDayTempDescription.text = get.weather?.get(0)?.description.toString()
            binding.textCardDayTemp.text = get.main?.temp.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = DayTempCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val dayIndex = position % 7
        val day = getDayOfWeek(dayIndex)
        val dayData = dataList[dayIndex]
        holder.bind(dayData)
        holder.binding.textCardDay.text = day
    }

    override fun getItemCount(): Int {
        return 7
    }

    private fun getDayOfWeek(dayIndex: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_WEEK, dayIndex)
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}