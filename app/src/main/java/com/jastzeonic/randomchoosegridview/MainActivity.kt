package com.jastzeonic.randomchoosegridview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    private var column = 1
    private var row = 1

    private var xPosition = 0
    private var yPosition = 0

    private var currentThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit_text_1.addTextChangedListener {
            column = getTextNumber(it.toString())
        }

        edit_text_2.addTextChangedListener {
            row = getTextNumber(it.toString())
        }

        button_ok.setOnClickListener {

            switchLayout()

            genGridView()

            startNewThread()

        }


    }

    override fun onResume() {
        super.onResume()
        if (currentThread != null) {
            startNewThread()
        }
    }


    override fun onPause() {
        super.onPause()
        currentThread?.interrupt()
    }


    private fun startNewThread() {
        currentThread = genCounterThread()
        currentThread?.start()
    }

    private fun genCounterThread() = Thread {

        try {
            Thread.sleep(10000)
        } catch (exception: InterruptedException) {
            return@Thread
        }

        runOnUiThread {

            clearSelectedPosition()


            val x = Random.nextInt(1, column + 1)
            val y = Random.nextInt(1, row + 1)

            xPosition = x - 1
            yPosition = y - 1

            val selectedColumn = result_view.getChildAt(xPosition) as ViewGroup
            selectedColumn.isSelected = true
            val selectedRow = selectedColumn.getChildAt(yPosition) as TextView
            selectedRow.text = "Random"

        }
        startNewThread()

    }

    private fun clearSelectedPosition() {
        val selectedColumn = result_view.getChildAt(xPosition) as ViewGroup
        selectedColumn.isSelected = false
        val selectedRow = selectedColumn.getChildAt(yPosition) as TextView
        selectedRow.text = ""
    }


    private fun genGridView() {

        val viewItemWidth = result_view.width / column
        val viewItemHeight = result_view.height / (row + 1)
        val colors = arrayListOf(
            Color.parseColor("#F5E3E3"),
            Color.parseColor("#E1F3E0"),
            Color.parseColor("#F9E9F8")
        )

        for (l in 0 until column) {
            val columnView = layoutInflater.inflate(R.layout.item_column_layout, result_view, false) as ViewGroup
            val columnLayoutParams = columnView.layoutParams
            columnLayoutParams.width = viewItemWidth
            columnView.layoutParams = columnLayoutParams
            for (n in 0 until row) {
                val rowView = layoutInflater.inflate(R.layout.item_text_layout, columnView, false)
                val rowLayoutParams = rowView.layoutParams
                rowView.setBackgroundColor(colors[n % colors.size])
                rowLayoutParams.height = viewItemHeight
                rowView.layoutParams = rowLayoutParams
                columnView.addView(rowView)
            }

            val button = layoutInflater.inflate(R.layout.item_button_layout, columnView, false)
            val buttonLayoutParams = button.layoutParams
            buttonLayoutParams.height = viewItemHeight
            button.layoutParams = buttonLayoutParams
            button.setOnClickListener {
                clearSelectedPosition()
            }
            columnView.addView(button)



            result_view.addView(columnView)

        }
    }

    private fun switchLayout() {
        input_view.visibility = View.GONE
        result_view.visibility = View.VISIBLE
    }


    private fun getTextNumber(text: String): Int {
        var textNumber = 0
        textNumber = try {
            text.toInt()
        } catch (exception: NumberFormatException) {
            1

        }
        return if (textNumber > 0) {
            textNumber
        } else {
            1

        }
    }
}
