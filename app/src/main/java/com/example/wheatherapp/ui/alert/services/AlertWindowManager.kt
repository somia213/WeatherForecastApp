package com.example.wheatherapp.ui.alert.services

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.getSystemService
import com.example.wheatherapp.R
import com.example.wheatherapp.databinding.AlertDisplayBinding

class AlertWindowManager(private val context: Context,private val description: String) {

    // WindowManager --> display UI on it
    private lateinit var windowManager: WindowManager
    private lateinit var view : View
    private lateinit var binding: AlertDisplayBinding

    fun initializeWindowManager(){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.alert_display , null)
        binding = AlertDisplayBinding.bind(view)

        // set data
        binding.textDescription.text = description
        binding.btnOk.setOnClickListener{
            closeWindowManager()
            closeService()
        }

        // Initialize view
        val LAYOUT_FLAG:Int =if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }else{
            WindowManager.LayoutParams.TYPE_PHONE
        }
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
        val params = WindowManager.LayoutParams(
            width ,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE ,
            PixelFormat.TRANSLUCENT
        )
        windowManager.addView(view,params)
    }

    private fun closeWindowManager(){
        try {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(view)
            view.invalidate()
            (view.parent as ViewGroup).removeAllViews()
        }catch (e:Exception){
            Log.d("Error",e.toString())
        }
    }
    private fun closeService(){
        context.stopService(Intent(context,AlertService::class.java))
    }
}
