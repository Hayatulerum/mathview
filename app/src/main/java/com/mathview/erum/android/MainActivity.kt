package com.mathview.erum.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mathview.erum.android.lib.MathView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val math: MathView = findViewById(R.id.formula1)
        math.text = MathView.inLine(text())
        math.textSize = "1"

        math.isVerticalScrollBarEnabled = false
        math.isHorizontalScrollBarEnabled = false
        math.settings.displayZoomControls = false
        math.settings.builtInZoomControls = true
        math.settings.setSupportZoom(true)

    }

    private fun text(): String {
        val string = StringBuilder()

        string.append(" \\\\frac{87}{8790}")

        return string.toString()
    }
}