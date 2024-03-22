package com.example.wheatherapp.ui.alert.view

import Repository
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.wheatherapp.R
import com.example.wheatherapp.convertDateToLong
import com.example.wheatherapp.data.local.FavouriteDataBase
import com.example.wheatherapp.data.models.AlertModel
import com.example.wheatherapp.dayConverterToString
import com.example.wheatherapp.timeConverterToString
import com.example.wheatherapp.ui.alert.services.AlertPeriodicWorkManager
import com.example.wheatherapp.ui.alert.viewmodel.AlertViewModel
import com.example.wheatherapp.ui.alert.viewmodel.AlertViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

class Alert_dialogFragment : DialogFragment() {

    private lateinit var view: View
    private lateinit var fromBtn : Button
    private lateinit var toBtn : Button
    private lateinit var saveBtn : Button
    private lateinit var cancelBtn : Button

    // AlertModel
    private lateinit var alertModel: AlertModel

    // Initialize viewModel
    private val viewModel : AlertViewModel by lazy {
        val repository = Repository(requireContext())
        ViewModelProvider(
            requireActivity(),
            AlertViewModelFactory(repository)
        )[AlertViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setPeriodWorkManger(id: Long) {

        val data = Data.Builder()
        data.putLong("id", id)

        // can handle more than one constraint
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            AlertPeriodicWorkManager::class.java,
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "$id",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alert_dialog,container,false)

        fromBtn = view.findViewById(R.id.btn_From)
        toBtn = view.findViewById(R.id.btn_to)
        saveBtn = view.findViewById(R.id.btn_save)
        cancelBtn = view.findViewById(R.id.btn_cancel)
        // Initialize values of date and time
        // setInitialData()

        fromBtn.setOnClickListener{
            showDatePicker(true)
        }

        toBtn.setOnClickListener{
            showDatePicker(false)
        }

        saveBtn.setOnClickListener{
            viewModel.insertAlert(alertModel)
            dialog!!.dismiss()
        }

        cancelBtn.setOnClickListener{
            dialog!!.dismiss()
        }

        lifecycleScope.launch {
            viewModel.stateInsetAlert.collect{id ->
                // Register Worker Here and send Id of alert

                setPeriodWorkManger(id)
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setInitialData() {
        val rightNow = Calendar.getInstance()

        // init day
        val year = rightNow.get(Calendar.YEAR)
        val month = rightNow.get(Calendar.MONTH)
        val day = rightNow.get(Calendar.DAY_OF_MONTH)
        val date = "$day/${month + 1}/$year"
        val dayNow = convertDateToLong(date, requireContext())
        val currentDate = dayConverterToString(dayNow,requireContext())

        // init time
        val currentHour = TimeUnit.HOURS.toSeconds(rightNow.get(Calendar.HOUR_OF_DAY).toLong())
        val currentMinute = TimeUnit.MINUTES.toSeconds(rightNow.get(Calendar.MINUTE).toLong())

        val currentTime = (currentHour + currentMinute).minus(3600L * 2)
        val currentTimeText =
            timeConverterToString((currentTime + 60),requireContext())

        val afterOneHour = currentTime.plus(3600L)
        val afterOneHourText =
            timeConverterToString(afterOneHour, requireContext())


        //init model
        alertModel = AlertModel(
            startTime = (currentTime + 60),
            endTime = afterOneHour,
            startDate = dayNow,
            endDate = dayNow
        )
        fromBtn.text = currentDate.plus("\n").plus(currentTimeText)
        toBtn.text = currentDate.plus("\n").plus(afterOneHourText)

    }

    private fun showDatePicker(isFrom: Boolean) {
        val myCalender = Calendar.getInstance()
        val year = myCalender[Calendar.YEAR]
        val month = myCalender[Calendar.MONTH]
        val day = myCalender[Calendar.DAY_OF_MONTH]
        val myDateListener =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                if (view.isShown) {
                    val date = "$day/${month + 1}/$year"
                    showTimePicker(
                        isFrom,
                        convertDateToLong(
                            date,
                            requireContext()
                        )
                    )
                }
            }
        val datePickerDialog = DatePickerDialog(
            requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            myDateListener, year, month, day
        )
        datePickerDialog.setTitle("Choose date")
        datePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        datePickerDialog.show()
    }

    private fun showTimePicker(isFrom: Boolean, datePicker: Long) {
        val rightNow = Calendar.getInstance()
        val currentHour = rightNow.get(Calendar.HOUR_OF_DAY)
        val currentMinute = rightNow.get(Calendar.MINUTE)
        val listener: (TimePicker?, Int, Int) -> Unit =
            { _: TimePicker?, hour: Int, minute: Int ->
                val time = TimeUnit.MINUTES.toSeconds(minute.toLong()) +
                        TimeUnit.HOURS.toSeconds(hour.toLong()) - (3600L * 2)
                val dateString = dayConverterToString(datePicker ,requireContext())
                val timeString =
                    timeConverterToString(time, requireContext())
                val text = dateString.plus(" \n").plus(timeString)
                if (isFrom) {
                    alertModel.startTime = time
                    alertModel.startDate = datePicker
                    fromBtn.text = text
                } else {
                    alertModel.endTime = time
                    alertModel.endDate = datePicker
                    toBtn.text = text
                }
            }

        val timePickerDialog = TimePickerDialog(
            requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            listener, currentHour, currentMinute, false
        )

        timePickerDialog.setTitle(("Choose time"))
        timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }


    companion object {

    }
}