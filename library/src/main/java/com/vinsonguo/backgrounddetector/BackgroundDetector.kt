package com.vinsonguo.backgrounddetector

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SCREEN_OFF
import android.content.Intent.ACTION_SCREEN_ON
import android.content.IntentFilter
import android.os.Bundle

/**
 * determine if the app is switched to the foreground/background
 */
typealias BackgroundDetectListener = (isBackground: Boolean) -> Unit

class BackgroundDetector : Application.ActivityLifecycleCallbacks, BroadcastReceiver() {

    private val listeners = mutableListOf<BackgroundDetectListener>()

    private var startedActivityCount = 0

    /**
     * Visible activities are any activity started but not stopped yet. An activity can be paused
     * yet visible: this will happen when another activity shows on top with a transparent background
     * and the activity behind won't get touch inputs but still need to render / animate.
     */
    private var hasVisibleActivities: Boolean = false

    /**
     * Assuming screen on by default.
     */
    private var isForeground: Boolean = true

    private var lastUpdate: Boolean = false
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // ignore
    }

    override fun onActivityStarted(activity: Activity) {
        startedActivityCount++
        if (!hasVisibleActivities && startedActivityCount == 1) {
            hasVisibleActivities = true
            updateVisible()
        }
    }

    override fun onActivityResumed(activity: Activity) {
        // ignore
    }

    override fun onActivityPaused(activity: Activity) {
        // ignore
    }

    override fun onActivityStopped(activity: Activity) {
        // This could happen if the callbacks were registered after some activities were already
        // started. In that case we effectively considers those past activities as not visible.
        if (startedActivityCount > 0) {
            startedActivityCount--
        }
        if (hasVisibleActivities && startedActivityCount == 0 && !activity.isChangingConfigurations) {
            hasVisibleActivities = false
            updateVisible()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // ignore
    }

    override fun onActivityDestroyed(activity: Activity) {
        // ignore
    }

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        isForeground = intent.action != ACTION_SCREEN_OFF
        updateVisible()
    }

    private fun updateVisible() {
        val visible = isForeground && hasVisibleActivities
        if (visible != lastUpdate) {
            lastUpdate = visible
            listeners.forEach { it.invoke(!visible) }
        }
    }

    fun addListener(listener: BackgroundDetectListener) = listeners.add(listener)

    fun removeListener(listener: BackgroundDetectListener) = listeners.remove(listener)

    fun listenerSize() = listeners.size
}

private var detector: BackgroundDetector? = null

fun Application.addBackgroundDetectListener(listener: BackgroundDetectListener): Boolean {
    if (detector == null) {
        val _detector = BackgroundDetector()
        registerActivityLifecycleCallbacks(_detector)
        registerReceiver(_detector, IntentFilter().apply {
            addAction(ACTION_SCREEN_ON)
            addAction(ACTION_SCREEN_OFF)
        })
        detector = _detector
    }
    return detector?.addListener(listener) ?: false
}

fun Application.removeBackgroundDetectListener(listener: BackgroundDetectListener): Boolean {
    val result = detector?.removeListener(listener) ?: false
    if (detector != null && detector!!.listenerSize() == 0) {
        unregisterReceiver(detector)
        detector = null
    }
    return result
}