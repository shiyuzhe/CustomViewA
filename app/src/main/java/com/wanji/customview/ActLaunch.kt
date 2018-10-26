package com.wanji.customview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wanji.customview.base64.ActVideoPlay
import com.wanji.customview.base64.ImageByte
import kotlinx.android.synthetic.main.activity_main.*

/**
 *   by  :   syz
 *   Time: 2018/10/26 15:48
 *   Description:
 */
class ActLaunch : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_CONTACTS)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) run {
                Log.e("syz", "request  success")
            }
        }
    }
}