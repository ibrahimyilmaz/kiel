package me.ibrahimyilmaz.kiel.listadapter

import androidx.recyclerview.widget.DiffUtil
import me.ibrahimyilmaz.kiel.core.AdapterFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderRenderer

class RecyclerViewListAdapterFactory<T : Any> :
    AdapterFactory<T, RecyclerViewListAdapter<T, RecyclerViewHolder<T>>>() {

    private var itemDiffUtil: DiffUtil.ItemCallback<T>? = null

    fun itemDiffUtil(lambda: () -> DiffUtil.ItemCallback<T>) {
        this.itemDiffUtil = lambda()
    }

    override fun create() =
        RecyclerViewListAdapter<T, RecyclerViewHolder<T>>(
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