package me.ibrahimyilmaz.kiel.item

import androidx.annotation.LayoutRes

interface LayoutResourceRenderer<out T : Any> : Renderer<T> {

    @get:LayoutRes
    val layoutRes: Int
}