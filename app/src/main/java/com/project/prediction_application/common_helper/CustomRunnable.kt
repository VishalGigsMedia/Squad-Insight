package com.project.prediction_application.common_helper

import android.os.Handler
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CustomRunnable(var handler: Handler, var holder: TextView, var endDate: String) :
    Runnable {
    //var millisUntilFinished: Long = 40000
    override fun run() {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        formatter.isLenient = false
        val curDate = Date()
        /*  val curMillis = curDate.time
          val curTime = formatter.format(curDate)*/
        val startDate = formatter.format(curDate)

        /*val seconds = millisUntilFinished / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val time = ("Time Left " + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60)
        //val time = days.toString() + " " + "days" + " :" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60
        holder.text = time
        millisUntilFinished -= 1000
        Log.d("DEV123", time)
        handler.postDelayed(this, 1000)*/


        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        // Try Block

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            val d1 = sdf.parse(startDate)
            val d2 = sdf.parse(endDate)

            // Calucalte time difference
            // in milliseconds
            val differenceInTime = d2.time - d1.time

            // Calucalte time difference in
            // seconds, minutes, hours, years,
            // and days
            val differenceInSeconds = ((differenceInTime
                    / 1000)
                    % 60)
            val differenceInMinutes = ((differenceInTime
                    / (1000 * 60))
                    % 60)
            val differenceInHours = ((differenceInTime
                    / (1000 * 60 * 60))
                    % 24)
            val differenceInYears = (differenceInTime
                    / (1000L * 60 * 60 * 24 * 365))
            val differenceInDays = ((differenceInTime
                    / (1000 * 60 * 60 * 24))
                    % 365)

            // Print the date difference in
            // years, in days, in hours, in
            // minutes, and in seconds
            print("Difference " + "between two dates is: ")
            println(
                (differenceInYears.toString() + " years, "
                        + differenceInDays
                        + " days, "
                        + differenceInHours
                        + " hours, "
                        + differenceInMinutes
                        + " minutes, "
                        + differenceInSeconds
                        + " seconds")
            )

            if (differenceInHours.toInt() == 0 && differenceInMinutes.toInt() == 0 && differenceInSeconds.toInt() == 0) {
                handler.removeCallbacksAndMessages(true)
                //millisUntilFinished = 0
                holder.text = "Time up"
            } else {
                val timeLeft =
                    "$differenceInHours : $differenceInMinutes : $differenceInSeconds"
                holder.text = timeLeft
                //millisUntilFinished -= 1000
                handler.postDelayed(this, 100)
            }

        } // Catch the Exception
        catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    /*init {
        this.millisUntilFinished = millisUntilFinished
    }*/
}