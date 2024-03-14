package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity(), Runnable {
    private var seconds = 0
    private var running = false
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
        }

        handler = Handler()
        runStopwatch()
    }

    override fun run() {
        val txtTime: TextView = findViewById(R.id.txtTime)

        var hours = seconds / 3600
        var minutes = (seconds % 3600) / 60
        val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds % 60)
        txtTime.text = time

        if (running) {
            seconds++
        }

        handler.postDelayed(this, 1000)
    }

    private fun runStopwatch() {
        handler.post(this)
    }

    fun onClickStart(view: View) {
        running = true
    }

    fun onClickStop(view: View) {
        running = false
    }

    fun onClickReset(view: View) {
        running = false
        seconds = 0
        val txtTime: TextView = findViewById(R.id.txtTime)
        txtTime.text = "0:00:00"
    }
}
