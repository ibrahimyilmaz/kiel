package me.ibrahimyilmaz.kiel.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SimpleArrayMap

class RecyclerViewHolderFactory<T : Any, VH : RecyclerViewHolder<T>>(
    private val viewHolderMap: SimpleArrayMap<Int, ViewHolderFactory<T, VH>>,
    private val viewHolderCreatedListeners: SimpleArrayMap<Int, OnViewHolderCreated<VH>>
) {

    fun instantiate(parent: ViewGroup, viewType: Int): RecyclerViewHolder<T> {
        val viewHolderFactory = checkNotNull(viewHolderMap[viewType]) {
            "ViewHolder is not found for provided viewType:$viewType"
        }

        return viewHolderFactory.instantiate(
            LayoutInflater.from(parent.context).inflate(
                viewType,
                parent,
                false
            )
        ).also {
            viewHolderCreatedListeners[viewType]?.invoke(it)
        }
    }
}