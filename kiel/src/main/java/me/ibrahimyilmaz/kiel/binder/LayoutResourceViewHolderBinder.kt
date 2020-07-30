package me.ibrahimyilmaz.kiel.binder

import androidx.annotation.LayoutRes

interface LayoutResourceViewHolderBinder<out T : Any> :
    ViewHolderBinder<T> {

    @get:LayoutRes
    val layoutRes: Int

    override val itemViewType: Int
        get() = layoutRes
}