@file:JvmName("ViewExt")

package android.view

import android.app.Activity
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

inline fun View.whenApplyWindowInsets(crossinline completion: (View, WindowInsetsCompat) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        completion(view, insets)
        insets
    }
}

fun View.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val newFlags = this.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        this.systemUiVisibility = newFlags
    }
}

fun View.clearLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = this.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        this.systemUiVisibility = flags
    }
}

fun View.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = false
): Int {
    context.theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun View.getTintedDrawableFrom(@DrawableRes drawableRes: Int, @ColorRes colorRes: Int): Drawable? {
    val tintColor = ContextCompat.getColor(context, colorRes)
    val drawable = ContextCompat.getDrawable(context, drawableRes)
    return drawable?.apply { DrawableCompat.setTint(this, tintColor) }
}


/**
 * Searches for the matching context ignoring reference that implement [ContextWrapper].
 */
internal fun View.activity(): Activity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}
