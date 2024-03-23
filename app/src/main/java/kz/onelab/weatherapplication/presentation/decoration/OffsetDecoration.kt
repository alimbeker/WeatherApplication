package kz.onelab.weatherapplication.presentation.decoration

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
class OffsetDecoration(start: Int = 0, top: Int = 0, end: Int = 0, bottom: Int = 0) :
    ItemDecoration() {
    private val startOffset = start.dp
    private val topOffset = top.dp
    private val endOffset = end.dp
    private val bottomOffset = bottom.dp

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = startOffset
        outRect.top = topOffset
        outRect.right = endOffset
        outRect.bottom = bottomOffset
    }
}

val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()


val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )