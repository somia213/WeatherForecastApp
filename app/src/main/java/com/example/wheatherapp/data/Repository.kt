import android.content.Context
import com.example.wheatherapp.data.local.FavouriteDataBase
import com.example.wheatherapp.data.local.FavouriteEntity
import com.example.wheatherapp.data.models.WeatherResponse
import com.example.wheatherapp.data.remote.ApiCalls
import com.example.wheatherapp.data.remote.RetrofitInstance

class Repository( context: Context) {

    private lateinit var network: ApiCalls
    private lateinit var room: FavouriteDataBase

    init {
        network = RetrofitInstance().api
        room = FavouriteDataBase.invoke(context)
    }

    // Local
    suspend fun getFavourites(): List<FavouriteEntity> {
        return room.favouriteDao().getFavourites()
    }

    suspend fun insertFavourites(favourite: FavouriteEntity) {
        room.favouriteDao().insertFavourites(favourite)
    }

    suspend fun deleteFavourite(favourite: FavouriteEntity) {
        room.favouriteDao().deleteFavourite(favourite)
    }

    // Remote
    suspend fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {
        val response = network.getWeatherDetails(
            latitude = latitude,
            longitude = longitude
        )
        if (response.isSuccessful) {
            return response.body() ?: WeatherResponse()
        }
        return WeatherResponse()
    }
}