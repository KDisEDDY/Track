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

    var config: XLogConfig = XLogConfig("sunline_user")

    fun init(context: Context, config: XLogConfig? = null) {
        sdCard = context.applicationContext.getExternalFilesDir(null)?.absolutePath ?: ""
        config?.let {
            XLogManager.config = it
        }
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
            if (BuildConfig.DEBUG) {
                Log.setConsoleLogOpen(true)
                Log.appenderFlush()
                Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, XLogManager.config.getFileName(context), XLogManager.config.cacheDay)
            }
        }
    }

    fun registerLifecycleCallback(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // 加个兼容逻辑判断
                if (activityCount < 0) activityCount = 0
                android.util.Log.d(TAG, "the activity account onCreate $activityCount")
                activityCount += 1
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                activityCount -= 1
                android.util.Log.d(TAG, "the activity account onDestroyed $activityCount")
                if (activityCount <= 0) {
                    android.util.Log.d(TAG, "append flush")
                    // 每次退出app时都写入日志
                    if (isStartSingleLog) {
                        Log.appenderFlushSync(true)
                    } else {
                        Log.appenderFlush()
                    }
                    closeLog()
                }
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
        Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, config.getFileName(context), 0)
    }

    fun closeLog() {
        Log.appenderClose()
    }

}