package com.ljy.track

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import java.util.concurrent.atomic.AtomicBoolean


object XLogManager {

    private const val TAG = "XLogManager"
    private var sdCard = ""
    private var logPath = ""
    private var cachePath = ""

    var isInit = AtomicBoolean(false)

    var activityCount = 0

    // 是否触发了单次日志上传逻辑
    var isStartSingleLog = false

    fun init(context: Context) {
        sdCard = context.applicationContext.getExternalFilesDir(null)?.absolutePath ?: ""
        logPath = "$sdCard/log"
        if (isInit.compareAndSet(false, true)) {

            System.loadLibrary("c++_shared")
            System.loadLibrary("marsxlog")

            android.util.Log.d(TAG, "sdcard :$sdCard logPath: $logPath")
            // this is necessary, or may crash for SIGBUS
            cachePath = "${context.applicationContext.filesDir}/xlog"

            //init xlog
            val xlog = Xlog()

            Log.setLogImp(xlog)
            var logFileName = ""
            // todo 这里的开始收集日志要增加触发时机，这里先简单验证一下
            if (BuildConfig.DEBUG) {
                Log.setConsoleLogOpen(true)
                Log.appenderFlush()
                logFileName = "youyu_zero_debug_${context.applicationContext.applicationInfo.processName.replace(".", "_")}"
                Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, logFileName, 15)
            }
        }
    }

    fun registerLifecycleCallback(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityCount += 1
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                activityCount -= 1
                if (activityCount <= 0) {
                    // 每次退出app时都写入日志
                    Log.appenderFlush()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })
    }

    /**
     * 开始单点记录
     */
    fun startSingleLog(context: Context) {
        isStartSingleLog = true
        if (!isInit.get()) {
            init(context)
        }
        Log.setConsoleLogOpen(false)
        Log.appenderFlush()
        val logFileName = "youyu_zero_release_${context.applicationContext.applicationInfo.processName.replace(".", "_")}"
        // 调低缓存日志的时间，可能是生产日志
        Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, logFileName, 1)
    }

    fun closeLog() {
        Log.appenderClose()
    }


}