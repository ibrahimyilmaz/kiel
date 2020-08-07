package me.ibrahimyilmaz.kiel.core

import android.view.View

typealias ViewHolderCreator<T> = (view: View) -> RecyclerViewHolder<T>

interface ViewHolderFactory<out T : Any, out VH : RecyclerViewHolder<T>> {
    fun instantiate(view: View): VH
}