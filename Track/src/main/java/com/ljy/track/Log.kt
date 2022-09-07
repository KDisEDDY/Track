package com.ljy.track

import com.tencent.mars.xlog.Log

object Log {

    fun f(tag: String?, msg: String?) {
        Log.f(tag, msg)
    }

    /**
     * use e(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    fun e(tag: String?, msg: String?) {
        Log.e(tag, msg)
    }

    /**
     * use w(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    fun w(tag: String?, msg: String?) {
        Log.w(tag, msg)
    }

    /**
     * use i(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    fun i(tag: String?, msg: String?) {
        Log.i(tag, msg)
    }

    /**
     * use d(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    fun d(tag: String?, msg: String?) {
        Log.d(tag, msg)
    }

    /**
     * use v(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    fun v(tag: String?, msg: String?) {
        Log.v(tag, msg)
    }

    fun f(tag: String?, format: String?, vararg obj: Any?) {
        Log.f(tag, format, obj)
    }

    fun e(tag: String?, format: String?, vararg obj: Any?) {
        Log.e(tag, format, obj)
    }

    fun w(tag: String?, format: String?, vararg obj: Any?) {
        Log.w(tag, format, obj)
    }

    fun i(tag: String?, format: String?, vararg obj: Any?) {
        Log.i(tag, format, obj)
    }

    fun d(tag: String?, format: String?, vararg obj: Any?) {
        Log.d(tag, format, obj)
    }

    fun v(tag: String?, format: String?, vararg obj: Any?) {
        Log.v(tag , format, obj)
    }
}