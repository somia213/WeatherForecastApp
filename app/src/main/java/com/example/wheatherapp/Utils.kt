package com.example.wheatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
fun dateTimeConverterTimestampToString(dt:Long,context :Context):String?{
    val timeStamp = Date(TimeUnit.SECONDS.toMillis(dt))
    return SimpleDateFormat("dd MMM , hh:mm aa" , getCurrentLocale(context)).format(timeStamp)
}

@SuppressLint("SimpleDateFormat")
fun dayConverterToString(dt:Long,context :Context):String?{
    val timeStamp = Date(TimeUnit.SECONDS.toMillis(dt))
    return SimpleDateFormat("dd MMM" , getCurrentLocale(context)).format(timeStamp)
}

@SuppressLint("SimpleDataFormat")
fun timeConverterToString(dt:Long,context :Context):String?{
    val timeStamp = Date(TimeUnit.SECONDS.toMillis(dt))
    return SimpleDateFormat("hh:mm aa" , getCurrentLocale(context)).format(timeStamp)
}

// to get locale is arabic or english
// you can not use it and in this case will take default if arabic be arabic and if denylist be english
// so its up to you

fun getCurrentLocale(context: Context):Locale?{
   return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
       context.resources.configuration.locales[0]
   }else{
       context.resources.configuration.locale
   }
}

fun convertDateToLong(date:String,context: Context):Long{
    val simpleDateFormat = SimpleDateFormat("dd/MM/YYYY ", getCurrentLocale(context))
    val timestamp:Date = simpleDateFormat.parse(date) as Date
    return timestamp.time
}