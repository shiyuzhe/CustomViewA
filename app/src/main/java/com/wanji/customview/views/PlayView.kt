package com.wanji.customview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min
import com.wanji.customview.R


/**
 *   by  :   syz
 *   Time: 2018/10/25 16:09
 *   Description:
 */
class PlayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var paint: Paint? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var isPlaying = false
    private var clickAble = true//是否可点击
    private val mAnimDuration = 200//动画时间

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        paint!!.style = Paint.Style.FILL
        paint!!.color = resources.getColor(R.color.color_f2f2f2)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
            mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
            mHeight = View.MeasureSpec.getSize(heightMeasureSpec)
            mWidth = min(mWidth, mHeight)
            mHeight = mWidth
        } else {
            val density = resources.displayMetrics.density
            mWidth = (density * 50).toInt()
            mHeight = mWidth
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val wid = mWidth.toFloat().div(2)
        canvas?.drawCircle(wid, wid, wid, paint!!)
        paint?.color = Color.GRAY
        paint?.strokeWidth = 10F
        val h = (mWidth / 3).toFloat()
        val w = (mWidth / 5).toFloat()
        canvas?.drawLine(2 * w, h, 2 * w, h * 2, paint!!)
        canvas?.drawLine(3 * w, h, 3 * w, h * 2, paint!!)

    }


    private fun toPlayAnimation() {
        Log.e("syz", "toPlayAnimation")
        isPlaying = true

    }

    private fun toPauseAnimation() {
        isPlaying = false
        Log.e("syz", "toPauseAnimation")
    }

    var playPauseListener: PlayPauseListener? = null
        set(value) {
            field = value
            setOnClickListener {
                if (!clickAble)
                    return@setOnClickListener
                clickAble = false
                isClickable = clickAble
                if (isPlaying) {
                    toPauseAnimation()
                    field?.pause()
                } else {
                    toPlayAnimation()
                    field?.play()
                }
                clickAble = true
                isClickable = clickAble
            }
        }

    interface PlayPauseListener {
        fun play()
        fun pause()
    }


}