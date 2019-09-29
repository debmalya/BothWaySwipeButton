package org.deb.widget.controller

import android.app.Notification
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class SwipeButtonController : AppCompatSeekBar {

    // If dragged beyond permissible limit, it will be considered as accepted
    private var permissiable: Int = 50
    private var confirmed: Boolean = false
    private var confirm: String = "Confirm"
    private var reject: String = "Reject"
    private var paint = Paint()
    private var canvas = Canvas()
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
//                canvas?.drawText(confirm, this.x+this.width/2 , this.y +this.height/2, paint)
            } else if (progress < permissiable && confirmed) {
                // assumption: once confirmed then only can be rejected
                // default mode is rejected
                setBackgroundColor(Color.rgb(200, 50, 70))
                confirmed = false
//                canvas?.drawText(reject, this.x+this.width/2 , this.y +this.height/2, paint)
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
        if (used) {
            var message: String = reject
            if (confirmed) {
                message = confirm
            }
            canvas?.drawText(message, this.x + this.width / 3, this.y + this.height / 2, paint)
        }
    }


    private fun init() {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
//        paint.textAlign= p
        paint.textSize = 50f;

    }

}