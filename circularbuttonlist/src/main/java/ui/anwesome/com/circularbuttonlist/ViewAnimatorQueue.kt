package ui.anwesome.com.circularbuttonlist

import android.view.View
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by anweshmishra on 30/12/17.
 */
class ViewAnimatorQueue(var view:View) {
    var animated:Boolean = false
    val state = State()
    val animations:ConcurrentLinkedQueue<(Float)->Unit> = ConcurrentLinkedQueue()
    fun addAnimation(cb:(Float)->Unit) {
        animations.add(cb)
    }
    fun animate() {
        if(animated) {
            state.update({scale ->
                animations.at(0)?.invoke(scale)
            },{
                animated = false
            })
            try {
                Thread.sleep(50)
                view.invalidate()
            } catch(ex: Exception) {

            }
        }
    }
    fun startAnimation() {
        if(!animated) {
            animated = true
            view.postInvalidate()
        }
    }
}
data class State(var scale:Float = 0f) {
    fun update(updatecb:(Float)->Unit,stopcb:()->Unit) {
        scale += 0.1f
        updatecb(scale)
        if(scale > 1) {
            scale = 0f
            stopcb()
        }
    }
}
fun ConcurrentLinkedQueue<(Float)->Unit>.at(i:Int):((Float)->Unit)? {
    var index = 0
    this.forEach {
        if(index == i) {
            return it
        }
        index++
    }
    return null
}