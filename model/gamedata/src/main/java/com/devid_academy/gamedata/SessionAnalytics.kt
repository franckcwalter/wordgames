package com.devid_academy.gamedata

import android.content.res.Resources
import android.os.Build

class SessionAnalytics {
    private enum class SessionKey(val key: String) {
        GAME_NAME("gameName"),
        DEVICE_MODEL("deviceModel"),
        ANDROID_VERSION("androidVersion"),
        SCREEN_RESOLUTION("screenResolution"),
        SESSION_START_TIME("sessionStartTime"),
        METRICS("metrics"),
        SESSION_DURATION("sessionDuration")
    }

    private val sessionData = mutableMapOf<String, Any>()
    private val metrics = mutableMapOf<String, Int>()

    init {
        sessionData[SessionKey.DEVICE_MODEL.key] = Build.MODEL
        sessionData[SessionKey.ANDROID_VERSION.key] = Build.VERSION.SDK_INT
        sessionData[SessionKey.SCREEN_RESOLUTION.key] =
            "${Resources.getSystem().displayMetrics.widthPixels}x${Resources.getSystem().displayMetrics.heightPixels}"
    }

    fun startSession(gameName: String) {
        sessionData[SessionKey.GAME_NAME.key] = gameName
        sessionData[SessionKey.SESSION_START_TIME.key] = System.currentTimeMillis()
    }

    fun incrementMetric(name: String, amount: Int = 1) {
        metrics[name] = (metrics[name] ?: 0) + amount
    }

    fun addData(key: String, value: Any) {
        sessionData[key] = value
    }

    fun getSessionData(): Map<String, Any> {
        return buildMap {
            putAll(sessionData)
            put(SessionKey.METRICS.key, metrics)
            put(
                SessionKey.SESSION_DURATION.key,
                System.currentTimeMillis() - (sessionData[SessionKey.SESSION_START_TIME.key] as? Long ?: 0L)
            )
        }
    }

    fun reset() {
        val deviceModel = sessionData[SessionKey.DEVICE_MODEL.key] ?: Build.MODEL
        val androidVersion = sessionData[SessionKey.ANDROID_VERSION.key] ?: Build.VERSION.SDK_INT
        val screenResolution = sessionData[SessionKey.SCREEN_RESOLUTION.key] ?:
        "${Resources.getSystem().displayMetrics.widthPixels}x${Resources.getSystem().displayMetrics.heightPixels}"

        sessionData.clear()
        metrics.clear()

        sessionData[SessionKey.DEVICE_MODEL.key] = deviceModel
        sessionData[SessionKey.ANDROID_VERSION.key] = androidVersion
        sessionData[SessionKey.SCREEN_RESOLUTION.key] = screenResolution
    }

}
