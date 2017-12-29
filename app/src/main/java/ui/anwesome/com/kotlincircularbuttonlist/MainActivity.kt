package ui.anwesome.com.kotlincircularbuttonlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.circularbuttonlist.CircularButtonListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = CircularButtonListView.create(this)
        view.addText("Hello")
        view.addText("More")
        view.addText("Store")
        view.addText("Please")
        view.addToParent(this)
    }
}
