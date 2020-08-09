package me.ibrahimyilmaz.kiel.core

import androidx.collection.SimpleArrayMap

class RecyclerViewHolderRenderer<T : Any, VH : RecyclerViewHolder<T>>(
    private val itemTypes:
        SimpleArrayMap<Class<*>, Int>,
    private val bindViewHolderListeners:
        SimpleArrayMap<Class<*>, OnBindViewHolder<T, RecyclerViewHolder<T>>>,
    private val viewHolderBoundWithPayloadListeners:
        SimpleArrayMap<Class<*>, OnBindViewHolderWithPayload<T, RecyclerViewHolder<T>>>
) {

    fun getItemViewType(t: T) = checkNotNull(itemTypes[t.javaClass]) {
        "Item Type is not defined for ${t.javaClass.name}"
    }

    fun renderViewHolder(
        holder: VH,
        position: Int,
        item: T
    ) = holder.bind(position, item).also {
        bindViewHolderListeners[item.javaClass]?.invoke(holder, position, item)
    }

    fun renderViewHolder(
        holder: VH,
        position: Int,
        item: T,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            renderViewHolder(holder, position, item)
        } else {
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
}