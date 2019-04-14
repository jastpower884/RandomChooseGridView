package com.jastzeonic.randomchoosegridview

import android.graphics.Color
import android.graphics.ColorMatrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var column = 1
    private var row = 1


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


        }


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
