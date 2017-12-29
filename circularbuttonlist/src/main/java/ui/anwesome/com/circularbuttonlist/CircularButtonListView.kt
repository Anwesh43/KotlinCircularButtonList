package ui.anwesome.com.circularbuttonlist

/**
 * Created by anweshmishra on 30/12/17.
 */
import android.content.*
import android.graphics.*
import android.view.*
import java.util.LinkedList
import java.util.concurrent.ConcurrentLinkedQueue

class CircularButtonListView(ctx:Context):View(ctx) {
    val texts:LinkedList<String> = LinkedList()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = CircularButtonListRenderer(this)
    override fun onDraw(canvas:Canvas) {
        renderer.render(canvas,paint)
    }
    fun addText(text:String) {
        texts.add(text)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap(event.x,event.y)
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
    data class CircularButtonList(var w:Float,var h:Float,var texts:LinkedList<String>,var gap:Float = 0f) {
        val buttons:ConcurrentLinkedQueue<CircularButton> = ConcurrentLinkedQueue()
        val updatingButtons:ConcurrentLinkedQueue<CircularButton> = ConcurrentLinkedQueue()
        var curr:CircularButton?=null
        init {
            if(texts.size > 0) {
                gap = h/(2*texts.size+1)
                val x = w/2
                var y = 3*gap/2
                texts.forEach {
                    buttons.add(CircularButton(it,x,y,3*gap/4))
                    updatingButtons.add(CircularButton(it,x,y,3*gap/4))
                    y += 2*gap
                }
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            buttons.forEach { button ->
                button.draw(canvas,paint)
            }
        }
        fun updateLeft(scale:Float) {
            updatingButtons.forEach {
                it.updateLeft(scale,w+2*gap)
            }
        }
        fun updateDown(scale:Float) {
            curr?.updateDown(scale,h+2*gap)
        }
        fun handleTap(x:Float,y:Float,startcb:()->Unit) {
            buttons.forEach {
                if(it.handleTap(x,y)) {
                    curr = it
                    updatingButtons.remove(it)
                    startcb()
                    return
                }
            }
        }
    }
    data class CircularButtonListRenderer(var view:CircularButtonListView,var time:Int = 0) {
        val animatorQueue = ViewAnimatorQueue(view)
        var buttonList:CircularButtonList?=null
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                buttonList = CircularButtonList(w,h,view.texts)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            buttonList?.draw(canvas,paint)
            time++
            animatorQueue.animate()
        }
        fun handleTap(x:Float,y:Float) {
            buttonList?.handleTap(x,y,{
                animatorQueue.addAnimation {scale ->
                    buttonList?.updateLeft(scale)
                }
                animatorQueue.addAnimation {scale ->
                    buttonList?.updateDown(scale)
                }
                animatorQueue.startAnimation()
            })
        }
    }
}