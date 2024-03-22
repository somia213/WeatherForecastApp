package com.example.wheatherapp.ui.alert.services

import Repository
import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.wheatherapp.convertDateToLong
import com.example.wheatherapp.data.models.AlertModel
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AlertPeriodicWorkManager(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    val repository = Repository(context)


    override suspend fun doWork(): Result {
        if (!isStopped) {
            val data = inputData
            val id = data.getLong("id", -1)
          //  getData(id.toInt())
        }
        return Result.success()
    }

//    private suspend fun getData(id: Int) {
//
//        // handle request data from room or retrofit
////        val currentWeather = repository ???????????????????????????????????????????????
//        val alert = repository.getAlert(id)
//
//        if (checkTime(alert)) {
//            val delay: Long = getDelay(alert)
//            if (currentWeather.alerts.isNullOrEmpty()) {
//                setOneTimeWorkManger(
//                    delay,
//                    alert.id,
////                    currentWeather?.current?.weather?.get(0)?.description?:"" ,
////
////                )
//            } else {
//                setOneTimeWorkManger(
//                    delay,
//                    alert.id,
//                    currentWeather?.alerts?.get(0)tags?.get(0)?:"",
//                )
//            }
//        } else {
//            repository.deleteAlert(id)
//            WorkManager.getInstance().cancelAllWorkByTag("$id")
//        }
//    }

    private fun setOneTimeWorkManger(delay: Long, id: Int?, description: String) {
        val data = Data.Builder()
        data.putString("description", description)
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(
            OneTimeWorkManager::class.java,
        )
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "$id",
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }

    private fun getDelay(alert: AlertModel): Long {
        val hour =
            TimeUnit.HOURS.toSeconds(Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong())
        val minute =
            TimeUnit.MINUTES.toSeconds(Calendar.getInstance().get(Calendar.MINUTE).toLong())
        return (alert.startTime ?: 0) - ((hour + minute) - (2 * 3600L))
    }

    private fun checkTime(alert: AlertModel): Boolean {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val date = "$day/${month + 1}/$year"
        val dayNow = convertDateToLong(date, context)
        return dayNow >= (alert.startDate ?: 0)
                &&
                dayNow <= (alert.endDate ?: 0)
    }

}