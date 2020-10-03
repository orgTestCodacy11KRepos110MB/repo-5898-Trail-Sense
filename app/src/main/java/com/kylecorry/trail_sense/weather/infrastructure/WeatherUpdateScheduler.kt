package com.kylecorry.trail_sense.weather.infrastructure

import android.content.Context
import com.kylecorry.trail_sense.shared.tasks.DeferredTaskScheduler
import com.kylecorry.trail_sense.shared.tasks.ITaskScheduler
import com.kylecorry.trail_sense.weather.infrastructure.receivers.WeatherUpdateAlarmReceiver
import com.kylecorry.trail_sense.weather.infrastructure.services.WeatherUpdateService
import com.kylecorry.trailsensecore.infrastructure.system.NotificationUtils
import java.time.Duration

object WeatherUpdateScheduler {
    fun start(context: Context) {
        val scheduler = getScheduler(context)
        scheduler.schedule(Duration.ZERO)
    }

    fun stop(context: Context) {
        NotificationUtils.cancel(context, WeatherNotificationService.WEATHER_NOTIFICATION_ID)
        val scheduler = getScheduler(context)
        scheduler.cancel()
        context.stopService(WeatherUpdateService.intent(context))
    }

    fun getScheduler(context: Context): ITaskScheduler {
        return WeatherUpdateWorker.scheduler(context)
    }
}