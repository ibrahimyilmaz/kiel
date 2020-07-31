package me.ibrahimyilmaz.kiel.renderer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import me.ibrahimyilmaz.kiel.binder.ViewBinder
import kotlin.reflect.KFunction1

class Renderer<out T : Any>(
    val key: Class<*>,
    @get:LayoutRes
    val layoutRes: Int,
    private val viewBinderIntrospection: KFunction1<View, ViewBinder<T>>
) {

    val itemViewType: Int
        get() = layoutRes

    fun createView(
        parent: ViewGroup
    ): View = LayoutInflater.from(parent.context).inflate(
        layoutRes,
        parent,
        false
    )

    fun render(
        view: View,
        item: @UnsafeVariance T
    ) = view.viewBinder.bind(item)

    fun render(
        view: View,
        item: @UnsafeVariance T,
        payload: List<Any>
    ) = view.viewBinder.bind(item, payload)

    @Suppress("UNCHECKED_CAST")
    private inline val View.viewBinder: ViewBinder<T>
        get() {
            if (tag == null) {
                tag = viewBinderIntrospection(this)
            }
            return tag as ViewBinder<T>
        }
}

