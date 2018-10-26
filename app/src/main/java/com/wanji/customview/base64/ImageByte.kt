package com.wanji.customview.base64

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.wanji.customview.R
import kotlinx.android.synthetic.main.activity_image_byte.image_view

class ImageByte : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_byte)
        Thread(Runnable {
            val bytes = Base64Convert.get(applicationContext, "base64bbb.txt")
            val option: BitmapFactory.Options = BitmapFactory.Options()
            option.inPreferredConfig = Bitmap.Config.ALPHA_8
            val bitmap = BitmapUtil.getPicFromBytes(bytes, option)
            runOnUiThread {
                image_view.setImageBitmap(bitmap)
            }
        }).start()
    }
}
