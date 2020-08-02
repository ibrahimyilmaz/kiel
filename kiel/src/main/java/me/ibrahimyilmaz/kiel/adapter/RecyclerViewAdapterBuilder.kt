package me.ibrahimyilmaz.kiel.adapter

import android.view.View
import androidx.collection.SimpleArrayMap
import kotlin.reflect.KFunction1

class RecyclerViewAdapterBuilder<T : Any> {
    private val viewHolderMap =
        SimpleArrayMap<Int, KFunction1<View, RecyclerViewHolder<T>>>()

    private val rendererMap =
        SimpleArrayMap<Class<*>, Int>()

    private val viewHolderCreatedListeners =
        SimpleArrayMap<Int, OnViewHolderCreated<RecyclerViewHolder<T>>>()

    private val viewHolderBoundListeners =
        SimpleArrayMap<Class<*>, OnViewHolderBound<T, RecyclerViewHolder<T>>>()

    private val viewHolderBoundWithPayloadListeners =
        SimpleArrayMap<Class<*>, OnViewHolderBoundWithPayload<T, RecyclerViewHolder<T>>>()

    private var diffUtilCallbackFactory: RecyclerDiffUtilCallbackFactory<T>? = null

    fun diffUtilCallback(lambda: DiffUtilCallback<T>) {
        this.diffUtilCallbackFactory = object : RecyclerDiffUtilCallbackFactory<T> {
            override fun create(oldItems: List<T>, newItems: List<T>): RecyclerDiffUtilCallback<T> =
                lambda(oldItems, newItems)
        }
    }

    fun register(
        lambda: RecyclerViewAdapterRegistryBuilder<T>.() -> Unit
    ) {
        with(
            RecyclerViewAdapterRegistryBuilder<T>()
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

    fun build() =
        RecyclerViewAdapter(
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
            ), null
        )
}
