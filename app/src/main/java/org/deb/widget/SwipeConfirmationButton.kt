package org.deb.widget

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import kotlin.math.abs

class SwipeConfirmationButton : Button {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    // if (drag length / width ) ratio is greater than this state will be changed
    private var threshold = 0.5

    private var gradientColor1: Int = Color.rgb(0, 128, 0)
    private var gradientColor2: Int = Color.rgb(0, 102, 0)
    private var gradientColor3: Int = Color.rgb(0, 77, 0)

    private var gradientRejectColor1: Int = Color.rgb(204, 51, 0)
    private var gradientRejectColor2: Int = Color.rgb(179, 45, 0)
    private var gradientRejectColor3: Int = Color.rgb(153, 38, 0)
    private var gradientColor2Width: Int = 100

    private var afterConfirmationBackground: Int = Color.rgb(0,102,0)
    private var afterRejectBackgroundColor: Int = Color.rgb(200, 0, 0)
    private var defaultColor : Int = Color.rgb(153,153,102)

    // there are three state 0 - default, 1 - Confirmed, 2 - Rejected
    private var state: Int = 0

    // X coordinate of where user first touches the button
    private var x1: Float = 0.toFloat()

    // Y coordinate of where user first touches the button
    private var y1: Float = 0.toFloat()

    // At default state ( state = 0 ) this text will be displayed in button
    private var startButtonText = "Swipe right >>> to accept"

    // Whether threshold crossed or not
    private var confirmThresholdCrossed = false

    // Text will be displayed on the button after confirmation
    private var actionConfirmText = "CONFIRM"
    // Text will be displayed on the button after rejection
    private var actionRejectText = "REJECT"

    private lateinit var swipeCallBack : Swipe

    private var swiping = false
    private var initialState = 0

    private var xInitial  = 0.toFloat()


    fun addOnSwipeCallBack(swipeCallBack: Swipe){
        this.swipeCallBack = swipeCallBack
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        if (this.isEnabled && this.isClickable ) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                x1 = event.x
                y1 = event.y
                initialState = state
                swipeCallBack?.onPress()
            } else if (event.action == MotionEvent.ACTION_UP) {
                swiping = false
                when (state) {
                    1 -> {
                        this.text = actionConfirmText
                        this.setBackgroundColor(afterConfirmationBackground)
                        this.confirmThresholdCrossed = true
                        swipeCallBack?.onConfirm()
                    }
                    2 -> {
                        this.text = actionRejectText
                        this.setBackgroundColor(afterRejectBackgroundColor)
                        this.confirmThresholdCrossed = false
                        swipeCallBack?.onCancel()
                    }
                    else -> {
                        this.text = startButtonText
                        this.setBackgroundColor(defaultColor)
                        confirmThresholdCrossed = false
                    }
                }

            } else if (event.action == MotionEvent.ACTION_MOVE) {
                val x2 = event.x
                this.text = startButtonText

                if (!swiping) {
                    // From where started swipe ( X coordinate )
                    xInitial = event.x
                    swiping = true
                }

                if (x1 < x2) {
                    // left to right swap ( CONFIRM flow )
                    this.background = ShapeDrawable(RectShape())
                    val mDrawable = ShapeDrawable(RectShape())
                    val actionConfirmFraction = threshold

                    var shader = LinearGradient(
                        x2, 0f, x2 - gradientColor2Width, 0f,
                        intArrayOf(gradientColor3, gradientColor2, gradientColor1),
                        floatArrayOf(0f, 0.5f, 1f),
                        Shader.TileMode.CLAMP
                    )

                    mDrawable.paint.shader = shader
                    val diff = x2 - xInitial

                    if (diff >= this.width*actionConfirmFraction){
                        confirmThresholdCrossed = true
                        this.text = actionConfirmText
                        state = 1
                    }else{
                        state = initialState
                    }
                    this.background = mDrawable
                } else if (x1 > x2) {
                    // right to left swap ( REJECT flow )
                    this.background = ShapeDrawable(RectShape())
                    val mDrawable = ShapeDrawable(RectShape())
                    val shader = LinearGradient(
                        x2, 0f, x2 - gradientColor2Width, 0f,
                        intArrayOf(gradientRejectColor3, gradientRejectColor2, gradientRejectColor1),
                        floatArrayOf(0f, 0.5f, 1f),
                        Shader.TileMode.CLAMP
                    )

                    mDrawable.paint.shader = shader

                    val diff =  xInitial - x2


                    Log.d("diff",diff.toString())
                    val confirmationFraction: Double = this.width * threshold
                    Log.d("threshold",confirmationFraction.toString())
                    if (diff >= confirmationFraction){
                        confirmThresholdCrossed = false
                        this.text = actionRejectText
                        state = 2
                    } else {
                        state = initialState
                    }
                    this.background = mDrawable
                }

            }
        }
         return true
    }

    /**
     * Swipe interface
     *
     */
    interface Swipe {
        fun onPress()
        fun onCancel()
        fun onConfirm()
    }

}