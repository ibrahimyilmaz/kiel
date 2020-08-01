package me.ibrahimyilmaz.kiel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.SimpleArrayMap
import kotlin.reflect.KFunction1

class RecyclerViewHolderFactory<T : Any, VH : RecyclerViewHolder<T>>(
    private val viewHolderMap: SimpleArrayMap<Int, KFunction1<View, VH>>,
    private val viewHolderCreatedListeners: SimpleArrayMap<Int, OnViewHolderCreated<VH>>
) {

    fun instantiate(parent: ViewGroup, viewType: Int): VH {
        val viewHolderFactory = checkNotNull(viewHolderMap[viewType]) {
            "ViewHolder is not found for provided viewType:$viewType"
        }

        return viewHolderFactory(
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