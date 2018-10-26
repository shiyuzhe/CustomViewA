package com.wanji.customview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.wanji.customview.views.PlayPauseView.PlayPauseListener
import com.wanji.customview.base64.ActVideoPlay
import com.wanji.customview.base64.ImageByte
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PlayPauseListener {
    override fun play() {
        Log.e("syz", "play")
    }

    override fun pause() {
        Log.e("syz", "pause")
    }

    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image.setOnClickListener { startActivity(Intent(this, ImageByte::class.java)) }
        video.setOnClickListener { startActivity(Intent(this, ActVideoPlay::class.java)) }
        playBtn.playPauseListener = this

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_CONTACTS)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) run {
                Log.e("syz", "request  success")
            }
        }
    }
}
