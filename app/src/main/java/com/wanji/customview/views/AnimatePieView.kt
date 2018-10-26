package com.wanji.customview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View

/**
 * by  :   syz
 * Time: 2018/10/19 14:21
 * Description:
 */
class AnimatePieView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val TAG = this.javaClass.simpleName

    private var paint1: Paint? = null
    private var paint2: Paint? = null
    private var paint3: Paint? = null

    private var mDrawRectf = RectF()

    init {
        paint1 = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        paint1!!.style = Paint.Style.STROKE
        paint1!!.strokeWidth = 80f
        paint1!!.color = Color.RED

        paint2 = Paint(paint1)
        paint2!!.color = Color.GREEN

        paint3 = Paint(paint1)
        paint3!!.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)
        val width = (width - paddingLeft - paddingRight).toFloat()
        val height = (height - paddingTop - paddingBottom).toFloat()
        canvas.translate(width / 2, height / 2)
        //半径
        val radius = (Math.min(width, height) / 2 * 0.85).toFloat()
        mDrawRectf.set(-radius, -radius, radius, radius)
        canvas.drawArc(mDrawRectf, 0f, 120f, false, paint1!!)
        canvas.drawArc(mDrawRectf, 120f, 120f, false, paint2!!)
        canvas.drawArc(mDrawRectf, 240f, 120f, false, paint3!!)
    }

    interface IPaintInfo {
        fun getValue(): Double
        @ColorInt
        fun getColor(): Int

        fun getDesc(): String
    }
}
