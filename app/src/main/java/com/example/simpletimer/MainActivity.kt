package com.example.simpletimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var timeValue = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeText = findViewById<TextView>(R.id.timeText)
        val startButton = findViewById<Button>(R.id.start)
        val stopButton = findViewById<Button>(R.id.stop)
        val resetButton = findViewById<Button>(R.id.reset)

        val runnable = object : Runnable {
            override fun run() {
                timeValue++
                timeToText(timeValue)?.let {
                    // timeToText(timeValue)の値がlet内ではitとして使える
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        // start
        startButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            handler.post(runnable)
        }

        // stop
        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }


        // reset
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            timeToText()?.let {
                timeText.text = it
            }
        }
    }
    private fun timeToText(time: Int = 0): String? {
        return when {
            time < 0 -> {
                null
            }
            time == 0 -> {
                "00:00:00"
            }
            else -> {
                val h = time / 3600
                val m = time % 3600 / 60
                val s = time % 60
                "%1$02d:%2$02d:%3$02d".format(h, m, s)
            }
        }
    }
}
