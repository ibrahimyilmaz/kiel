package me.ibrahimyilmaz.kiel.adapter

import me.ibrahimyilmaz.kiel.core.AdapterFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderRenderer

class RecyclerViewAdapterFactory<T : Any> :
    AdapterFactory<T, RecyclerViewAdapter<T, RecyclerViewHolder<T>>>() {

    private var diffUtilCallbackFactory: RecyclerDiffUtilCallbackFactory<T>? = null

    fun diffUtilCallback(diffUtilCallBack: DiffUtilCallback<T>) {
        this.diffUtilCallbackFactory = object : RecyclerDiffUtilCallbackFactory<T> {
            override fun create(oldItems: List<T>, newItems: List<T>): RecyclerDiffUtilCallback<T> =
                diffUtilCallBack(oldItems, newItems)
        }
    }

    override fun create() = RecyclerViewAdapter<T, RecyclerViewHolder<T>>(
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
        diffUtilCallbackFactory
    )
}
