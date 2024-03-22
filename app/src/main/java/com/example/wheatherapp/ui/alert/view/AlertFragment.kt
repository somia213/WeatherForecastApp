package com.example.wheatherapp.ui.alert.view

import Repository
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import androidx.work.WorkManager
import com.example.wheatherapp.R
import com.example.wheatherapp.ui.alert.viewmodel.AlertViewModel
import com.example.wheatherapp.ui.alert.viewmodel.AlertViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlertFragment : Fragment() {

    private lateinit var view: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlertAdapter
    private lateinit var floatingActionButton: FloatingActionButton
    // private var _binding: FragmentAlertBinding? = null

    private val viewModel: AlertViewModel by lazy {
        val repository = Repository(requireContext())
        ViewModelProvider(
            requireActivity(),
            AlertViewModelFactory(repository)
        )[AlertViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_alert, container, false)


        recyclerView = view.findViewById(R.id.alertRecyclerView)
        floatingActionButton = view.findViewById(R.id.btn_add_alert)


        adapter = AlertAdapter(requireContext()) {
            viewModel.deleteAlert(it)

            // Periodic worker
            WorkManager.getInstance().cancelAllWorkByTag("${it.id}")

        }

        recyclerView.adapter = adapter

        viewModel.getAlerts()

        lifecycleScope.launch {
            viewModel.stateGetAlert.collect {
                adapter.differ.submitList(it)
            }
        }
    floatingActionButton.setOnClickListener{
        // Initialize here Alert Dialog fragment
       // AlertDialog().show(requireActivity().supportFragmentManager,"AlertDialogFragment")
    }
        settingsManager()

        return view
    }

//    val secondaryLayout = inflater.inflate(R.layout.secondary_layout, null)
//    val notificationRadio: RadioButton = secondaryLayout.findViewById(R.id.radio_notification)


////////////////////////////Error ////////////////////////////////////////


    private fun settingsManager(){
        // setting design and shared preference
        val notificationRadio:RadioButton = view.findViewById(R.id.radio_notification)
        val dialogRadio:RadioButton = view.findViewById(R.id.radio_dailog)

        // Initialize shared preference
        val sharedPreferences = requireContext().getSharedPreferences("MySettings",Context.MODE_PRIVATE)
        val editor =sharedPreferences.edit()

        //Return state if radio buttons
        val isDialogState = sharedPreferences.getBoolean("IsDialog",false)
        if(isDialogState){
            notificationRadio.isChecked = false
            dialogRadio.isChecked = true
        }else {
            notificationRadio.isChecked = true
            dialogRadio.isChecked = false
        }
        notificationRadio.setOnCheckedChangeListener{_,isChecked ->
           if (isChecked){
               editor.putBoolean("IsDialog",false)
               editor.apply()
           }
        }

        dialogRadio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                editor.putBoolean("IsDialog",true)
                editor.apply()

                 checkedPermissionOfOverlay()
            }
        }
    }
    private fun checkedPermissionOfOverlay(){
        // prepare permission for Display on top and Alarm Notification
        // checked if we already have permission

        if (!Settings.canDrawOverlays(requireContext())){

            val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            alertDialogBuilder.setTitle(R.string.displayOnTop)
                .setMessage("You should let us to draw on top")
                .setPositiveButton("Okay"){
                    dialog:DialogInterface,_:Int ->
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package" + requireContext().applicationContext.packageName)
                    )
                    startActivityForResult(intent,1)
                    dialog.dismiss()
                }.setNegativeButton("No"){dialog:DialogInterface,_:Int ->
                    dialog.dismiss()
                }.show()
        }
    }
}

