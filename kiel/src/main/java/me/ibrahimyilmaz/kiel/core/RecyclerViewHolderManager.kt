package me.ibrahimyilmaz.kiel.core

import android.view.ViewGroup
import androidx.annotation.LayoutRes

class RecyclerViewHolderManager<T : Any, VH : RecyclerViewHolder<T>>(
    private val recyclerViewHolderFactory: RecyclerViewHolderFactory<T, VH>,
    private val renderer: RecyclerViewHolderRenderer<T, VH>
) {
    fun instantiate(
        parent: ViewGroup,
        viewType: Int
    ) = recyclerViewHolderFactory.instantiate(parent, viewType)

    fun onBindViewHolder(
        holder: VH,
        position: Int,
        item: T
    ) = renderer.renderViewHolder(holder, position, item)

    fun onBindViewHolder(
        holder: VH,
        position: Int,
        item: T,
        payloads: List<Any>
    ) = renderer.renderViewHolder(holder, position, item, payloads)

    @LayoutRes
    fun getItemViewType(item: T): Int = renderer.getItemViewType(item)
}