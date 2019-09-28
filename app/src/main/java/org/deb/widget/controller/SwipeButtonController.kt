package org.deb.widget.controller

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class SwipeButtonController : AppCompatSeekBar {

    // If dragged beyond permissible limit, it will be considered as accepted
    private var permissiable: Int = 85
    private var confirmed : Boolean = false
    private var message : String = "Confirm"

    fun setPermissible(limit : Int){
        permissiable = limit
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN) {
            if (thumb.bounds.contains(event.x.toInt(), event.y.toInt())) {
                parent.requestDisallowInterceptTouchEvent(true)
                super.onTouchEvent(event)
            }
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (progress > permissiable) {
                // Confirm
                Log.d("SWIPE :", "CONFIRMED")
                setBackgroundColor(Color.GREEN)
                confirmed = true
            } else if (progress < permissiable  && confirmed) {
                // assumption: once confirmed then only can be rejected
                // default mode is rejected
                Log.d("SWIPE :", "REJECTED")
                setBackgroundColor(Color.RED)
//                progress = 0
                confirmed = false
            }
            parent.requestDisallowInterceptTouchEvent(false)
            progress = 0
        } else {
            super.onTouchEvent(event)
        }
        return true
    }

}