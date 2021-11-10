package com.vinsonguo.sample

import android.app.Application
import com.vinsonguo.backgrounddetector.initBackgroundDetector

class BaseApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        initBackgroundDetector()
    }
}