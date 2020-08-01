package me.ibrahimyilmaz.kiel.adapter

import androidx.collection.SimpleArrayMap

class RecyclerViewHolderRenderer<T : Any, VH : RecyclerViewHolder<T>>(
    private val map: SimpleArrayMap<Class<*>, Int>,
    private val viewHolderBoundListeners: SimpleArrayMap<Class<*>, OnViewHolderBound<T, RecyclerViewHolder<T>>>,
    private val viewHolderBoundWithPayloadListeners: SimpleArrayMap<Class<*>, OnViewHolderBoundWithPayload<T, RecyclerViewHolder<T>>>
) {

    fun getItemViewType(t: T) = checkNotNull(map[t.javaClass]) {
        "Item Type is not defined for ${t.javaClass.name}"
    }

    fun renderViewHolder(
        holder: VH,
        position: Int,
        item: T
    ) = holder.bind(position, item).also {
        viewHolderBoundListeners[item.javaClass]?.invoke(holder, position, item)
    }

    fun renderViewHolder(
        holder: VH,
        position: Int,
        item: T,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty())
            renderViewHolder(holder, position, item)
        else
            holder.bind(position, item, payloads).also {
                viewHolderBoundWithPayloadListeners[item.javaClass]?.invoke(
                    holder,
                    position,
                    item,
                    payloads
                )
            }
    }

}