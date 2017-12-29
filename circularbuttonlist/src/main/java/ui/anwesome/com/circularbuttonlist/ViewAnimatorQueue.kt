package ui.anwesome.com.circularbuttonlist

import android.view.View
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by anweshmishra on 30/12/17.
 */
class ViewAnimatorQueue(var view:View) {
    var animated:Boolean = false
    val animations:ConcurrentLinkedQueue<(Float)->Unit> = ConcurrentLinkedQueue()
    fun addAnimation(cb:(Float)->Unit) {
        animations.add(cb)
    }
    fun animate() {
        if(animated) {
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