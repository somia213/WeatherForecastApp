import android.content.Context
import com.example.wheatherapp.data.local.FavouriteDataBase
import com.example.wheatherapp.data.local.FavouriteEntity
import com.example.wheatherapp.data.models.AlertModel
import com.example.wheatherapp.data.models.WeatherResponse
import com.example.wheatherapp.data.remote.ApiCalls
import com.example.wheatherapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository( context: Context) {

    private lateinit var network: ApiCalls
    private lateinit var room: FavouriteDataBase

    init {
        network = RetrofitInstance().api
        room = FavouriteDataBase.invoke(context)
    }

    // Local
     fun getFavourites(): Flow<List<FavouriteEntity>> {
        return room.favouriteDao().getFavourites()
    }

    suspend fun insertFavourites(favourite: FavouriteEntity) {
        room.favouriteDao().insertFavourites(favourite)
    }

    suspend fun deleteFavourite(favourite: FavouriteEntity) {
        room.favouriteDao().deleteFavourite(favourite)
    }

    // Remote
     fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    )= flow {
        val response = network.getWeatherDetails(
            latitude = latitude,
            longitude = longitude
        )
        if (response.isSuccessful) {
            emit(response.body() ?: WeatherResponse())
        }else{
            emit(WeatherResponse())
        }
    }

    fun getAlerts(): Flow<List<AlertModel>> {
        return room.alertDao().getAlerts()
    }

    //****************************** put return ***************************************************//
    suspend fun insertAlert(alert :AlertModel):Long {
       return room.alertDao().insertAlert(alert)
    }

    suspend fun deleteAlert(id:Int) {
        room.alertDao().deleteAlert(id)
    }

    fun getAlert(id:Int) : AlertModel{
        return room.alertDao().getAlert(id)
    }
}