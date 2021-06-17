package com.vinsonguo.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vinsonguo.backgrounddetector.BackgroundDetectListener
import com.vinsonguo.backgrounddetector.addBackgroundDetectListener
import com.vinsonguo.backgrounddetector.removeBackgroundDetectListener

class SecondActivity : AppCompatActivity() {
    private val listener: BackgroundDetectListener = {
        Log.d("BackgroundDetector", "App is in background $it")
        // do something
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        application.addBackgroundDetectListener(listener)

    }

    override fun onDestroy() {
        super.onDestroy()
        application.removeBackgroundDetectListener(listener)
    }
}