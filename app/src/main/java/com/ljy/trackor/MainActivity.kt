package com.ljy.trackor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ljy.track.Log

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "MainActivity create")
        findViewById<TextView>(R.id.to_next).setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity onDestroy")
    }
}