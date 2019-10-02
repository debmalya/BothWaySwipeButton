package org.deb.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.Button

class SwipeConfirmationButton : Button {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var threshold = 0.5

    private var gradientColor1: Int = Color.rgb(0, 128, 0)

}