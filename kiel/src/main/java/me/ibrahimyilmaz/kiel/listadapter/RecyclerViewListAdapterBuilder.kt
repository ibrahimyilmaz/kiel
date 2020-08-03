package me.ibrahimyilmaz.kiel.listadapter

import androidx.recyclerview.widget.DiffUtil
import me.ibrahimyilmaz.kiel.core.*

class RecyclerViewListAdapterBuilder<T : Any> :
    AdapterBuilder<T, RecyclerViewListAdapter<T, RecyclerViewHolder<T>>>() {

    private var itemDiffUtil: DiffUtil.ItemCallback<T>? = null

    fun itemDiffUtil(lambda: () -> DiffUtil.ItemCallback<T>) {
        this.itemDiffUtil = lambda()
    }

    override fun build(): RecyclerViewListAdapter<T, RecyclerViewHolder<T>> =
        RecyclerViewListAdapter<T, RecyclerViewHolder<T>>(
            RecyclerViewHolderManager(
                RecyclerViewHolderFactory(
                    viewHolderMap,
                    viewHolderCreatedListeners
                ),
                RecyclerViewHolderRenderer(
                    rendererMap,
                    viewHolderBoundListeners,
                    viewHolderBoundWithPayloadListeners
                )
            ),
            itemDiffUtil
        )
}