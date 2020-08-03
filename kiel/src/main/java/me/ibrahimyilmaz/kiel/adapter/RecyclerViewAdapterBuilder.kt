package me.ibrahimyilmaz.kiel.adapter

import me.ibrahimyilmaz.kiel.core.*

class RecyclerViewAdapterBuilder<T : Any> :
    AdapterBuilder<T, RecyclerViewAdapter<T, RecyclerViewHolder<T>>>() {

    private var diffUtilCallbackFactory: RecyclerDiffUtilCallbackFactory<T>? = null

    fun diffUtilCallback(diffUtilCallBack: DiffUtilCallback<T>) {
        this.diffUtilCallbackFactory = object : RecyclerDiffUtilCallbackFactory<T> {
            override fun create(oldItems: List<T>, newItems: List<T>): RecyclerDiffUtilCallback<T> =
                diffUtilCallBack(oldItems, newItems)
        }
    }

    override fun build(): RecyclerViewAdapter<T, RecyclerViewHolder<T>> =
        RecyclerViewAdapter<T, RecyclerViewHolder<T>>(
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
            diffUtilCallbackFactory
        )
}
