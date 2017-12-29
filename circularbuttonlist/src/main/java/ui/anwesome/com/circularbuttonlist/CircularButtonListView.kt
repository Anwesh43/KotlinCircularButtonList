package ui.anwesome.com.circularbuttonlist

/**
 * Created by anweshmishra on 30/12/17.
 */
import android.content.*
import android.graphics.*
import android.view.*
class CircularButtonListView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}