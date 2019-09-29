package org.deb.widget.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class SwipeButtonController : AppCompatSeekBar {

    // If dragged beyond permissible limit, it will be considered as accepted
    private var permissiable: Int = 50
    private var confirmed: Boolean = false
    private var confirm: String = "Confirm"
    private var reject: String = "Reject"
    private var paint = Paint()
    private var used : Boolean = false

    fun setPermissible(limit: Int) {
        permissiable = limit
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN) {
            if (thumb.bounds.contains(event.x.toInt(), event.y.toInt())) {
                parent.requestDisallowInterceptTouchEvent(true)
                super.onTouchEvent(event)
            } else {
                return false
            }
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (progress > permissiable && !confirmed) {
                // Confirm
                setBackgroundColor(Color.rgb(50, 200, 50))
                confirmed = true
                used = true

            } else if (progress < permissiable && confirmed) {
                // assumption: once confirmed then only can be rejected
                // default mode is rejected
                setBackgroundColor(Color.rgb(200, 50, 70))
                confirmed = false
            }
            parent.requestDisallowInterceptTouchEvent(false)
            progress = if (confirmed) {
                100
            } else {
                0
            }
        } else {
            super.onTouchEvent(event)
        }
        return true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var message: String = reject
        if (used) {
            paint.textSize = 50f
            paint.color = Color.WHITE
            if (confirmed) {
                message = confirm
            }
        }else {
            paint.textSize = 20f
            paint.color = Color.BLACK
            message = "Swipe right >>> to accept"
        }
        canvas?.drawText(message, this.x + this.width / 3, this.y + this.height / 2, paint)
    }


    private fun init() {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 50f

    }

}