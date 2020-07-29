package me.ibrahimyilmaz.kiel.item

import android.view.View
import android.view.ViewGroup

interface Renderer<out T : Any> {

    fun createView(parent: ViewGroup): View

    fun render(
        view: View,
        item: @UnsafeVariance T
    )

    fun render(
        view: View,
        item: @UnsafeVariance T,
        payload: List<Any>
    ) = render(view, item)

    val itemViewType: Int
}