package me.ibrahimyilmaz.kiel.core

import android.view.View
import androidx.collection.SimpleArrayMap
import androidx.recyclerview.widget.RecyclerView.Adapter
import me.ibrahimyilmaz.kiel.adapter.*
import kotlin.reflect.KFunction1

abstract class AdapterBuilder<T : Any, A : Adapter<RecyclerViewHolder<T>>> {
    internal val viewHolderMap =
        SimpleArrayMap<Int, KFunction1<View, RecyclerViewHolder<T>>>()

    internal val rendererMap =
        SimpleArrayMap<Class<*>, Int>()

    internal val viewHolderCreatedListeners =
        SimpleArrayMap<Int, OnViewHolderCreated<RecyclerViewHolder<T>>>()

    internal val viewHolderBoundListeners =
        SimpleArrayMap<Class<*>, OnViewHolderBound<T, RecyclerViewHolder<T>>>()

    internal val viewHolderBoundWithPayloadListeners =
        SimpleArrayMap<Class<*>, OnViewHolderBoundWithPayload<T, RecyclerViewHolder<T>>>()

    fun register(
        lambda: AdapterRegistryBuilder<T>.() -> Unit
    ) {
        with(
            AdapterRegistryBuilder<T>()
                .apply(lambda).build()
        ) {
            viewHolderMap.put(
                layoutResource,
                viewHolderIntrospection
            )
            rendererMap.put(type, layoutResource)

            onViewHolderCreated?.let {
                viewHolderCreatedListeners.put(layoutResource, it)
            }

            onViewHolderBound?.let {
                viewHolderBoundListeners.put(type, it)
            }

            onViewHolderBoundWithPayload?.let {
                viewHolderBoundWithPayloadListeners.put(type, it)
            }
        }
    }

    abstract fun build(): A
}