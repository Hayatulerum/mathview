package com.mathview.erum.android.lib

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import kotlin.properties.Delegates

class MathView : WebView {

    private val TAG: String = "MathView"

    constructor(context: Context) : this(context, null) {
        init(context, null, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int?) {

        this.settings.javaScriptEnabled = true
        this.settings.loadWithOverviewMode = true
        this.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        else
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        if (attrs != null) {
            val math = context.obtainStyledAttributes(attrs, R.styleable.MathView)

            if (math.hasValue(R.styleable.MathView_text))
                this.text = math.getString(R.styleable.MathView_text)

            math.recycle()
        }
        update()
    }

    var text: String? by Delegates.observable("") { _, old, new ->
        if (old != new)
            update()
        Log.i(TAG, text.toString())
    }

    var textColor: String? by Delegates.observable("#000000") { _, old, new ->
        if (old != new)
            update()
    }

    private fun update() = loadDataWithBaseURL(
        "file:///android_asset/",
        "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <script id=\"MathJax-script\" async src=\"mathjax/tex-chtml.js\">\n" +
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "    var s = \" $text \";" +
                "document.body.style.color = \"$textColor\";" +
                "document.write(s);\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>",
        "text/html", "UTF-8", null
    )

    companion object {
        const val LINE = "\\\\"
        const val H_LINE = "\\hline"
        const val V_LINE = "|"

        // Array
        const val ARRAY_START = "\\begin{array}"
        const val ARRAY_END = "\\end{array}"

        // Matrix
        const val MATRIX_START = "\\begin{matrix}"
        const val MATRIX_END = "\\end{matrix}"

        // Space
        const val SPACE = "\\enspace"

        // Table
        const val TABLE_ALIGN_RIGHT = "{rrrrr}"

        // And
        const val AND = "&"

        const val R_ARROW = "\\rarr"

        const val BOX = "\\\\boxed"

        const val TINY = "\\tiny"
        const val SCRIPT_SIZE = "\\scriptsize"
        const val FOOTNOTE_SIZE = "\\footnotesize"
        const val SMALL = "\\small"
        const val NORMAL_SIZE = "\\normalsize"
        const val LARGE = "\\large"

        // text
        const val TEXT_IT = "\\textit"
        const val TEXT_RM = "\\textrm"
        const val TEXT_SF = "\\textsf"
        const val TEXT_TT = "\\texttt"

        /*this method will return number inside a circle*/
        fun textCircle(text: String): String {
            return "\\enclose{circle}{$text}"
        }

        /* this method will return simple color*/
        fun color(colorName: String, text: String): String {
            return "{\\\\color{$colorName}$text}"
        }

        /* this method will return simple text*/
        fun text(text: String): String {
            return "\\text{$text}"
        }

        /*this method will return simple text color*/
        fun textColor(colorName: String, text: String): String {
            return "\\textcolor{$colorName}{$text}"
        }

        /*this method will return the latex into inline*/
        fun inLine(text: String): String {
            return "$$text$"
        }

        /*this method will return the latex into display form*/
        fun display(text: String): String {
            return "$$$text$$"
        }

        /*this method will return the super script*/
        fun sup_script(text: String, power: String): String {
            return "$text^{$power}"
        }

        /*this method will return the sub script*/
        fun sub_script(text: String, power: String): String {
            return "$text _{$power}"
        }

        /*this method will return fraction*/
        fun frac(numerator: String, denominator: String): String {
            return "\\frac{$numerator}{$denominator}"
        }

        /*this method will return fraction*/
        fun c_frac(numerator: String, denominator: String): String {
            return "\\cfrac{$numerator}{$denominator}"
        }

        /*this method will return fraction*/
        fun over(numerator: String, denominator: String): String {
            return "{$numerator \\over $denominator}"
        }

        /*this method will return square root*/
        fun root(text: String): String {
            return "\\sqrt{$text}"
        }

        /*this method will return nth root*/
        fun root(nth: String, text: String): String {
            return "\\sqrt[$nth]{$text}"
        }

        /*this method will return canceled text*/
        fun cancel(text: String): String {
            return "\\cancel{$text}"
        }

        /*this method will return canceled text*/
        fun b_cancel(text: String): String {
            return "\\bcancel{$text}"
        }

        /*this method will return canceled text*/
        fun x_cancel(text: String): String {
            return "\\xcancel{$text}"
        }

        /* this method will return vertical height*/
        fun v_space(height: String): String {
            return "$LINE[$height ex]"
        }

        /*this method will return division head part*/
        fun division(dividend: String, divisor: String): String {
            return "$dividend\\enclose{longdiv}{$divisor}"
        }

    }
}