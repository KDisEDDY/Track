package com.ljy.track

import android.content.Context

/**
 * 参数可以为用户的具体标识
 * @param cacheDay 指在多少天以后 从缓存目录移到日志目录 一般情况下填0即可。非0表示会在 cacheDir 目录下存放几天的日志
 */
data class XLogConfig(val filePrefix: String, val cacheDay: Int = 0) {
    /**
     * 拼接逻辑:
     *  1.用户自定义字段 + debug/release + 进程名最后一段 + 时间标识
     */
    fun getFileName(context: Context): String {
        var name = ""
        name = if (BuildConfig.DEBUG) {
            "${this.filePrefix}_"
        } else {
            "${this.filePrefix}_Release_"
        }
        name += (context.applicationContext.applicationInfo.processName.substringAfterLast(".", "Sunline"))

        return name
    }

}
