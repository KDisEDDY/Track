package com.ljy.trackor

import android.app.Application
import android.content.Context
import android.util.Log
import com.ljy.track.XLogManager

class BaseApplication : Application() {

    companion object {
        lateinit var mApplication: Context
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d("BaseApplication" , "attachBaseContext")
        base?.let {
            mApplication = it
        }
    }

    override fun onCreate() {
        super.onCreate()
        initLog()
    }

    private fun initLog() {
        Log.d("BaseApplication", "initLog")
        XLogManager.init(this)
        XLogManager.registerLifecycleCallback(this)
    }
}