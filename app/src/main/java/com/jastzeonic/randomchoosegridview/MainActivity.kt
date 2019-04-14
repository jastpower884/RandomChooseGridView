package com.jastzeonic.randomchoosegridview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var column = 1
    var row = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit_text_1.addTextChangedListener {
            column = getTextNumber(it.toString())
        }

        edit_text_2.addTextChangedListener {
            row = getTextNumber(it.toString())
        }
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
