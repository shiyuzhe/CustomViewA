package com.wanji.customview

import android.content.Context

/**
 *   by  :   syz
 *   Time: 2018/10/26 15:12
 *   Description:
 */
fun dp2px(context: Context, dpVal: Float): Int {
    val density = context.getResources().getDisplayMetrics().density
    return (density * dpVal + 0.5f).toInt()
}