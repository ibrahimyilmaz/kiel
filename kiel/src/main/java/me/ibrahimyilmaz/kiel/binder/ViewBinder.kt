package me.ibrahimyilmaz.kiel.binder

import android.view.View

abstract class ViewBinder<T>(private val itemView: View) {

    abstract fun bind(item: T)
    open fun bind(item: T, payload: List<Any>) = Unit
}