package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.binder.ViewHolderBinder

class RecyclerViewHolder<T : Any>(
    parent: ViewGroup,
    private val viewHolderBinder: ViewHolderBinder<T>
) : RecyclerView.ViewHolder(viewHolderBinder.createView(parent)) {

    fun bind(item: T) = viewHolderBinder.render(itemView, item)
    fun bind(item: T, payload: List<Any>) = viewHolderBinder.render(itemView, item, payload)
}