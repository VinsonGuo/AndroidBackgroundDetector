package com.vinsonguo.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vinsonguo.backgrounddetector.BackgroundDetectListener
import com.vinsonguo.backgrounddetector.addBackgroundDetectListener
import com.vinsonguo.backgrounddetector.removeBackgroundDetectListener

class MainActivity : AppCompatActivity() {
    private val listener: BackgroundDetectListener = {
        Log.d("BackgroundDetector", "App is in background $it")
        // do something
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        application.addBackgroundDetectListener(listener)

        findViewById<View>(R.id.btn_second).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        application.removeBackgroundDetectListener(listener)
    }
}