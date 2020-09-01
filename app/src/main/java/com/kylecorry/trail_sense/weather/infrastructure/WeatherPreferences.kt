package com.kylecorry.trail_sense.weather.infrastructure

import android.content.Context
import androidx.preference.PreferenceManager
import com.kylecorry.trail_sense.R
import com.kylecorry.trail_sense.shared.sensors.SensorChecker
import java.time.Duration

class WeatherPreferences(private val context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val sensorChecker = SensorChecker(context)

    val hasBarometer: Boolean
        get() = sensorChecker.hasBarometer()

    val shouldMonitorWeather: Boolean
        get() = sensorChecker.hasBarometer() && prefs.getBoolean(context.getString(R.string.pref_monitor_weather), true)

    val shouldShowWeatherNotification: Boolean
        get() = prefs.getBoolean(context.getString(R.string.pref_show_weather_notification), true)

    val shouldShowPressureInNotification: Boolean
        get() = prefs.getBoolean(context.getString(R.string.pref_show_pressure_in_notification), false)

    val useSeaLevelPressure: Boolean
        get() = prefs.getBoolean(context.getString(R.string.pref_use_sea_level_pressure), true)

    val pressureHistory: Duration
        get(){
            val raw = prefs.getString(context.getString(R.string.pref_pressure_history), "48") ?: "48"
            return Duration.ofHours(raw.toLong())
        }

    val sendStormAlerts: Boolean
        get() = prefs.getBoolean(context.getString(R.string.pref_send_storm_alert), true)

    val dailyForecastChangeThreshold: Float
        get() {
            return when (prefs.getString(
                context.getString(R.string.pref_forecast_sensitivity),
                "medium"
            ) ?: "medium") {
                "low" -> 0.75f
                "medium" -> 0.5f
                else -> 0.3f
            }
        }

    val hourlyForecastChangeThreshold: Float
        get() {
            return when (prefs.getString(
                context.getString(R.string.pref_forecast_sensitivity),
                "medium"
            ) ?: "medium") {
                "low" -> 2.5f
                "medium" -> 1.5f
                else -> 0.5f
            }
        }

    val stormAlertThreshold: Float
        get() {
            if (!sendStormAlerts) {
                return -4.5f
            }

            return when (prefs.getString(
                context.getString(R.string.pref_storm_alert_sensitivity),
                "medium"
            ) ?: "medium") {
                "low" -> -6f
                "medium" -> -4.5f
                else -> -3f
            }
        }

}