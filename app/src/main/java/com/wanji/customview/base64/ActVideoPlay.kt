package com.wanji.customview.base64

import android.media.MediaPlayer
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import com.wanji.customview.R
import kotlinx.android.synthetic.main.act_video_play.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

/**
 *   by  :   syz
 *   Time: 2018/10/22 15:59
 *   Description:
 */
class ActVideoPlay : AppCompatActivity() {
    private val filePath = "http://video.dushuren123.com/lecture1523517197.mp4"
    var isSurfaceCanPlay = false
    var isCreatingSurface = false
    val player by lazy {
        MediaPlayer()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_video_play)
        initView()
        start()
    }


    private fun initView() {
        player.setOnCompletionListener { it.start() }
        player.setOnPreparedListener {
            playBtn.isClickable = true
            Log.e("syz", "setOnPreparedListener")
            it.start()
        }
        player.setOnErrorListener(MediaPlayer.OnErrorListener { player, what, extra ->
            player.start()
            false
        })
        playBtn.setOnClickListener {
            if (checkSurfaceCanPlay()) {
                if (player.isPlaying) {
                    player.pause()
                    playBtn.text = "play"
                } else {
                    player.start()
                    playBtn.text = "pause"
                    img.visibility = View.GONE
                }
            } else {
                //preparing draw surface
                Log.e("syz", "preparing draw surface")
            }
        }
    }

    private fun checkSurfaceCanPlay(): Boolean {
        if (!isSurfaceCanPlay) {
            if (!isCreatingSurface) {
                val bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND)
                img.setImageBitmap(bitmap)
                surface.holder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        isSurfaceCanPlay = true
                        isCreatingSurface = false
                        player.setDisplay(holder)

                    }

                    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                                width: Int, height: Int) {
                        // TODO Auto-generated method stub
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                        isSurfaceCanPlay = false
                    }
                })
            } else {
                isCreatingSurface = true
            }
            return false
        } else {
            return true
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun start() {
        checkSurfaceCanPlay()
        playBtn.isClickable = false
        createVideo()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createVideo() {
        Thread(Runnable {
            val fileName = "/video.mp4"
            val ss = Environment.getExternalStorageDirectory().toString()
            val file: File = File(ss + File.separator + "wj")
            if (!file.exists()) {
                file.mkdir()
            }
            val file1 = File(file.absolutePath, fileName)
            if (file1.exists()) {
                runOnUiThread {
                    Log.e("syz", "file1.exists")
                    val uri = Uri.parse(file1.absolutePath.toString())
                    player.setDataSource(this, uri)
                    player.prepareAsync()
                }
            } else {
                var outputStream: OutputStream? = null
                try {
                    val bytes = Base64Convert.get(this, "base64.txt")
                    outputStream = FileOutputStream(file1)
                    outputStream.write(bytes)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("syz", "write err:" + e.message)
                } finally {
                    outputStream?.close()
                }
                runOnUiThread {
                    Log.e("syz", "byte[]2mp4Success")
                    player.setDataSource(file1.absolutePath.toString())
                    player.prepareAsync()
                }
            }


        }).start()
    }


    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onDestroy() {
        if (player.isPlaying) {
            player.pause()
            player.release()
        }
        super.onDestroy()
    }
}