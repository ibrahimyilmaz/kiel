package me.ibrahimyilmaz.kiel.adapter

import androidx.recyclerview.widget.DiffUtil
import me.ibrahimyilmaz.kiel.core.AdapterFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderRenderer

typealias AreItemsTheSame<T> = (old: T, new: T) -> Boolean
typealias AreContentsTheSame<T> = (old: T, new: T) -> Boolean
typealias GetChangePayload<T> = (oldItem: T, newItem: T) -> Any?

class RecyclerViewAdapterFactory<T : Any> :
    AdapterFactory<T, RecyclerViewAdapter<T, RecyclerViewHolder<T>>>() {

    private var itemDiffUtil: DiffUtil.ItemCallback<T>? = null

    fun diff(lambda: () -> DiffUtil.ItemCallback<T>) {
        this.itemDiffUtil = lambda()
    }

    fun diff(
        areItemsTheSame: AreItemsTheSame<T>,
        areContentsTheSame: AreContentsTheSame<T>,
        getChangePayload: GetChangePayload<T>? = null
    ) {
        itemDiffUtil = object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(
                oldItem: T,
                newItem: T
            ) = areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(
                oldItem: T,
                newItem: T
            ) = areContentsTheSame(oldItem, newItem)

            override fun getChangePayload(oldItem: T, newItem: T) =
                getChangePayload?.let { it(oldItem, newItem) } ?: super.getChangePayload(
                    oldItem,
                    newItem
                )
        }
    }

    override fun create() =
        RecyclerViewAdapter<T, RecyclerViewHolder<T>>(
            RecyclerViewHolderManager(
                RecyclerViewHolderFactory(
                    viewHolderMap,
                    viewHolderCreatedListeners
                ),
                RecyclerViewHolderRenderer(
                    layoutResourceMap,
                    onBindViewHolderListeners,
                    onBindViewHolderWithPayloadListeners
                )
            ),
            itemDiffUtil
        )
}