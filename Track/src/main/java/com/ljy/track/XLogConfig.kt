package com.ljy.track

import android.content.Context

/**
 * 参数可以为用户的具体标识
 */
data class XLogConfig(val filePrefix: String, val cacheDay: Int = 15) {
    /**
     * 拼接逻辑:
     *  1.用户自定义字段 + debug/release + 进程名最后一段 + 时间标识
     *  @param isExactTime 是否需要时分秒标识 true 需要 false 不需要
     */
    fun getFileName(context: Context, isExactTime: Boolean = false): String {
        var name = ""
        name = if (BuildConfig.DEBUG) {
            "${this.filePrefix}_Debug_"
        } else {
            "${this.filePrefix}_Release_"
        }

        name += (context.applicationContext.applicationInfo.processName.substringAfterLast(".", "Sunline"))

        return name
    }

}
