package com.example.swipebutton

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.deb.widget.SwipeConfirmationButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val confirmationButton = findViewById<SwipeConfirmationButton>(R.id.confirmationButton).also {
            it.addOnSwipeCallBack(object: SwipeConfirmationButton.Swipe{
                override fun onPress() {
//                    Log.d("SWIPE :","PRESS")
                }

                override fun onCancel() {
//                    Log.d("SWIPE :","CANCEL")
                }

                override fun onConfirm() {
//                    Log.d("SWIPE :","CONFIRM")
                }

            })
        }

    }
}
