# BothWaySwipeButton
To create an android widget so that it will swipe to mark CONFIRMATION to prevent accidental button press. 
Right swipe to accept ( CONFIRM ). Swipe left to reject ( REJECT ).

## Behaviour
 ![Initial Screen](/docs/InitialScreen.png)
 ![Right Swipe](/docs/SwipeRight.png)
 ![Left Swipe](/docs/SwipeLeft.png)


## How to use it. 
```
<org.deb.widget.controller.SwipeButtonController
        android:id="@+id/sb_swipe"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentLeft="true"
        android:layout_centerVertical="true"
        android:layout_marginTop="100dp"
        android:layout_marginBottom="298dp"
        android:clickable="false"
        android:max="100"
        android:maxHeight="35dp"
        android:minHeight="35dp"
        android:progressDrawable="@android:color/transparent"
        android:splitTrack="false"
        android:thumb="@drawable/sb_thumb_bg"
        android:thumbOffset="20dp"
        app:layout_constraintBottom_toTopOf="@+id/textView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:ignore="UnusedAttribute"
        />
   ```
## Reference
[Got the idea form gratusik/AGIKSwipeButton](https://github.com/gratusik/AGIKSwipeButton)
