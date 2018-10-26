package com.wanji.customview.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanji.customview.R
import com.wanji.customview.dp2px

/**
 *   by  :   syz
 *   Time: 2018/10/26 14:26
 *   Description:
 */
class PlayPauseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    private var mWidth: Int = 0 //View宽度
    private var mHeight: Int = 0 //View高度
    private var mPaint: Paint? = null
    private var mLeftPath: Path = Path() //暂停时左侧竖条Path
    private var mRightPath: Path = Path() //暂停时右侧竖条Path
    private var gapWidth: Float = 0.toFloat() //两个暂停竖条中间的空隙,默认为两侧竖条的宽度
    private var mProgress: Float = 0.toFloat() //动画Progress
    private var mRect: Rect? = null
    /* ------------下方是参数------------- */

    private var isPlaying: Boolean = false
    private var mRectWidth: Float = 0.toFloat()  //圆内矩形宽度
    private var mRectHeight: Float = 0.toFloat() //圆内矩形高度
    private var mRectLT: Float = 0.toFloat()  //矩形左侧上侧坐标
    private var mRadius: Float = 0.toFloat()  //圆的半径
    private var bgColor = resources.getColor(R.color.color_f2f2f2)
    private var btnColor = Color.GRAY
    var direction = Direction.POSITIVE.value
    private var spacePadding: Float = 0.toFloat()
    private var animDuration = 2000//动画时间

    private var playPauseAnim: ValueAnimator? = null
        get() {
            val animator = if (isPlaying)
                ValueAnimator.ofFloat(1.toFloat(), 0.toFloat())
            else
                ValueAnimator.ofFloat(0.toFloat(), 1.toFloat())
            animator.duration = animDuration.toLong()
            animator.addUpdateListener { animation ->
                mProgress = animation.animatedValue as Float
                invalidate()
            }
            return animator
        }


    init {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mRect = Rect()
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //Measure btn width and height ,1：1
        mWidth = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY ||
                MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        } else {
            dp2px(context, 50f)
        }
        mHeight = mWidth
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = w
        mWidth = mHeight
        initValue()
    }

    private fun initValue() {
        mRadius = (mWidth / 2).toFloat()
        spacePadding = if (spacePadding == 0f) mRadius / 3f else spacePadding
        if (spacePadding > mRadius / Math.sqrt(2.0) || spacePadding < 0) {
            spacePadding = mRadius / 3f //默认值
        }
        val space = (mRadius / Math.sqrt(2.0) - spacePadding).toFloat() //矩形宽高的一半
        mRectLT = mRadius - space
        val rectRB = mRadius + space
        mRect?.top = mRectLT.toInt()
        mRect?.bottom = rectRB.toInt()
        mRect?.left = mRectLT.toInt()
        mRect?.right = rectRB.toInt()
        mRectWidth = 2 * space
        mRectHeight = 2 * space
        gapWidth = if (gapWidth != 0f) gapWidth else mRectWidth / 3
        mProgress = (if (isPlaying) 0 else 1).toFloat()
        animDuration = if (animDuration < 0) 200 else animDuration
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mLeftPath.rewind()
        mRightPath.rewind()
        mPaint!!.color = bgColor
        canvas.drawCircle(mWidth.toFloat() / 2, mHeight.toFloat() / 2, mRadius, mPaint!!)
        val distance = gapWidth * (1 - mProgress)  //暂停时左右两边矩形距离
        val barWidth = mRectWidth / 2 - distance / 2     //一个矩形的宽度
        val leftLeftTop = barWidth * mProgress       //左边矩形左上角
        val rightLeftTop = barWidth + distance       //右边矩形左上角
        val rightRightTop = 2 * barWidth + distance  //右边矩形右上角
        val rightRightBottom = rightRightTop - barWidth * mProgress //右边矩形右下角
        mPaint!!.color = btnColor
        mPaint!!.style = Paint.Style.FILL
        if (direction == Direction.NEGATIVE.value) {
            mLeftPath.moveTo(mRectLT, mRectLT)
            mLeftPath.lineTo(leftLeftTop + mRectLT, mRectHeight + mRectLT)
            mLeftPath.lineTo(barWidth + mRectLT, mRectHeight + mRectLT)
            mLeftPath.lineTo(barWidth + mRectLT, mRectLT)
            mLeftPath.close()

            mRightPath.moveTo(rightLeftTop + mRectLT, mRectLT)
            mRightPath.lineTo(rightLeftTop + mRectLT, mRectHeight + mRectLT)
            mRightPath.lineTo(rightRightBottom + mRectLT, mRectHeight + mRectLT)
            mRightPath.lineTo(rightRightTop + mRectLT, mRectLT)
            mRightPath.close()
        } else {
            mLeftPath.moveTo(leftLeftTop + mRectLT, mRectLT)
            mLeftPath.lineTo(mRectLT, mRectHeight + mRectLT)
            mLeftPath.lineTo(barWidth + mRectLT, mRectHeight + mRectLT)
            mLeftPath.lineTo(barWidth + mRectLT, mRectLT)
            mLeftPath.close()

            mRightPath.moveTo(rightLeftTop + mRectLT, mRectLT)
            mRightPath.lineTo(rightLeftTop + mRectLT, mRectHeight + mRectLT)
            mRightPath.lineTo(rightLeftTop + mRectLT + barWidth, mRectHeight + mRectLT)
            mRightPath.lineTo(rightRightBottom + mRectLT, mRectLT)
            mRightPath.close()
        }

        canvas.save()

        canvas.translate(mRectHeight / 8f * mProgress, 0.toFloat())
        val progress = if (isPlaying) 1 - mProgress else mProgress
        val corner = if (direction == Direction.NEGATIVE.value) -90 else 90
        val rotation = if (isPlaying) corner * (1 + progress) else corner * progress
        canvas.rotate(rotation, mWidth / 2f, mHeight / 2f)

        canvas.drawPath(mLeftPath, mPaint!!)
        canvas.drawPath(mRightPath, mPaint!!)

        canvas.restore()
    }

    private fun play() {
        if (playPauseAnim != null) {
            playPauseAnim?.cancel()
        }
        isPlaying = true
        playPauseAnim?.start()
    }

    private fun pause() {
        if (playPauseAnim != null) {
            playPauseAnim?.cancel()
        }
        isPlaying = false
        playPauseAnim?.start()
    }

    var playPauseListener: PlayPauseListener? = null
        set(value) {
            field = value
            setOnClickListener {
                if (isPlaying) {
                    pause()
                    field?.pause()
                } else {
                    play()
                    field?.play()
                }
            }
        }

    interface PlayPauseListener {
        fun play()
        fun pause()
    }


    enum class Direction constructor(//逆时针
            internal var value: Int) {
        POSITIVE(1), //顺时针
        NEGATIVE(2)
    }
}