package com.mathview.erum.android.lib

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlin.properties.Delegates


class MathView : WebView {

    private val TAG: String = "MathView"

    constructor(context: Context) : this(context, null) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun init(context: Context, attrs: AttributeSet?) {

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

        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);")
            }
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

    var textSize: String? by Delegates.observable("1") { _, old, new ->
        if (old != new)
        update()
    }

    private fun update() = loadDataWithBaseURL(
        "file:///android_asset/",
        "<html lang=\"en\">\n" +
                "<head>\n" +
                "<script>" +
                "MathJax = {" +
                "tex: {inlineMath: [['$', '$'], ['\\(', '\\)']]}, " +
                "svg: {fontCache: 'global'}, " +
                "chtml: {scale: $textSize,}};" +
                "</script>" +
                "<script type=\"text/javascript\" async src=\"mathjax/tex-chtml.js\">\n" +
                "</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "var s = \" $text \";" +
                "document.body.style.color = \"$textColor\";" +
                "document.write(s);\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>",
        "text/html", "UTF-8", null
    )

    companion object {

        /*this method will return the latex into inline*/
        fun inLine(text: String): String {
            return "$ $text $"
        }

        /*this method will return the latex into display form*/
        fun display(text: String): String {
            return "$$ $text $$"
        }
    }
}