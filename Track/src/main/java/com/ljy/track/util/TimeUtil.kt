package com.ljy.track.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    /**
     * 获取格式时间：yyyyMMddHHmmss
     */
    fun getFormatTime(): String {
        return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    }

    fun getFormatExactTime(): String {
        return SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
    }
}