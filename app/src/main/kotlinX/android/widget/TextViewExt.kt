package android.widget

import androidx.core.text.HtmlCompat

internal fun TextView.setHtml(source: String, mode: Int = HtmlCompat.FROM_HTML_MODE_LEGACY): TextView {
    text = HtmlCompat.fromHtml(source, mode)
    return this
}
