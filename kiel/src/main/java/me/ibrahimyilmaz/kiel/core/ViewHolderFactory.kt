package me.ibrahimyilmaz.kiel.core

import android.view.View

typealias ViewHolderCreator<VH> = (view: View) -> VH

interface ViewHolderFactory<out T : Any, out VH : RecyclerViewHolder<T>> {
    fun instantiate(view: View): VH
}