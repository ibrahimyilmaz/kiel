package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.renderer.Renderer

class RecyclerViewHolder<T : Any>(
    parent: ViewGroup,
    private val renderer: Renderer<T>
) : RecyclerView.ViewHolder(renderer.createView(parent)) {

    fun bind(item: T) = renderer.render(itemView, item)

    fun bind(item: T, payload: List<Any>) = renderer.render(itemView, item, payload)
}