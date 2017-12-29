package ui.anwesome.com.circularbuttonlist

/**
 * Created by anweshmishra on 30/12/17.
 */
import android.content.*
import android.graphics.*
import android.view.*
import java.util.LinkedList
class CircularButtonListView(ctx:Context):View(ctx) {
    val texts:LinkedList<String> = LinkedList()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    fun addText(text:String) {
        texts.add(text)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class CircularButton(var text:String,var x:Float,var y:Float,var r:Float,var ox:Float = x,var oy:Float = y) {
        fun draw(canvas:Canvas,paint:Paint) {
            paint.color = Color.parseColor("#673AB7")
            canvas.save()
            canvas.translate(x,y)
            canvas.drawCircle(0f,0f,r,paint)
            paint.textSize = r/5
            paint.color = Color.WHITE
            canvas.drawText(text,-paint.measureText(text)/2,r/10,paint)
            canvas.restore()
        }
        fun updateDown(scale:Float,h:Float) {
            y= oy +(h-oy)*scale
        }
        fun updateLeft(scale:Float,w:Float) {
            x = ox +(w-ox)*scale
        }
        fun handleTap(x:Float,y:Float):Boolean  = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r
    }
}