package me.ibrahimyilmaz.kiel.core

import android.view.View
import androidx.annotation.LayoutRes
import androidx.collection.SimpleArrayMap
import androidx.recyclerview.widget.RecyclerView.Adapter

abstract class AdapterFactory<T : Any, A : Adapter<RecyclerViewHolder<T>>> {
    internal val viewHolderMap =
        SimpleArrayMap<Int, ViewHolderFactory<T, RecyclerViewHolder<T>>>()

    internal val layoutResourceMap =
        SimpleArrayMap<Class<*>, Int>()

    internal val viewHolderCreatedListeners =
        SimpleArrayMap<Int, OnViewHolderCreated<RecyclerViewHolder<T>>>()

    internal val onBindViewHolderListeners =
        SimpleArrayMap<Class<*>, OnBindViewHolder<T, RecyclerViewHolder<T>>>()

    internal val onBindViewHolderWithPayloadListeners =
        SimpleArrayMap<Class<*>, OnBindViewHolderWithPayload<T, RecyclerViewHolder<T>>>()

    fun registerLayoutResource(
        type: Class<*>,
        layoutResource: Int
    ) {
        layoutResourceMap.put(type, layoutResource)
    }

    fun registerViewHolderFactory(
        layoutResource: Int,
        viewHolderFactory: ViewHolderFactory<T, RecyclerViewHolder<T>>
    ) {
        viewHolderMap.put(
            layoutResource,
            viewHolderFactory
        )
    }

    fun registerViewHolderCreatedListener(
        layoutResource: Int,
        value: OnViewHolderCreated<RecyclerViewHolder<T>>
    ) {
        viewHolderCreatedListeners.put(
            layoutResource,
            value
        )
    }

    fun registerViewHolderBoundListener(
        type: Class<*>,
        value: OnBindViewHolder<T, RecyclerViewHolder<T>>
    ) {
        onBindViewHolderListeners.put(
            type,
            value
        )
    }

    fun registerViewHolderBoundWithPayloadListener(
        type: Class<*>,
        value: OnBindViewHolderWithPayload<T, RecyclerViewHolder<T>>
    ) {
        onBindViewHolderWithPayloadListeners.put(
            type,
            value
        )
    }

    @Deprecated(
        "In general we try to avoid builder classes in Kotlin, as they're not needed and introduce nullability." +
                " So AdapterRegistryBuilder will be retired soon",
        ReplaceWith(
            "register( " +
                    "viewHolder = ," +
                    "layoutResource = ," +
                    "onViewHolderCreated = ," +
                    "onViewHolderBound = ," +
                    "onViewHolderBoundWithPayload = " +
                    ")"
        )
    )
    fun register(
        lambda: AdapterRegistryBuilder<T>.() -> Unit
    ) {
        with(
            AdapterRegistryBuilder<T>()
                .apply(lambda).build()
        ) {
            viewHolderMap.put(
                layoutResource,
                viewHolderFactory
            )
            layoutResourceMap.put(type, layoutResource)

            onViewHolderCreated?.let {
                registerViewHolderCreatedListener(layoutResource, it)
            }

            onBindViewHolder?.let {
                onBindViewHolderListeners.put(type, it)
            }

            onViewHolderBoundWithPayload?.let {
                onBindViewHolderWithPayloadListeners.put(type, it)
            }
        }
    }


    inline fun <reified P : T, reified VH : RecyclerViewHolder<P>> register(
        crossinline viewHolder: ViewHolderCreator<VH>,
        @LayoutRes
        layoutResource: Int,
        noinline onViewHolderCreated: OnViewHolderCreated<VH>? = null,
        noinline onBindBindViewHolder: OnBindViewHolder<P, VH>? = null,
        noinline onBindViewHolderWithPayload: OnBindViewHolderWithPayload<P, VH>? = null
    ) {
        val itemType = P::class.java

        val viewHolderFactory = object : ViewHolderFactory<P, VH> {
            override fun instantiate(view: View): VH = viewHolder(view)
        }

        registerViewHolderFactory(layoutResource, viewHolderFactory)


        registerLayoutResource(itemType, layoutResource)

        onViewHolderCreated?.let {
            val onViewHolderCreatedListener = object : OnViewHolderCreated<RecyclerViewHolder<T>> {
                override fun invoke(viewHolder: RecyclerViewHolder<T>) =
                    it(viewHolder as VH)
            }

            registerViewHolderCreatedListener(layoutResource, onViewHolderCreatedListener)
        }


        onBindBindViewHolder?.let {
            val onBindViewHolderListener = object : OnBindViewHolder<T, RecyclerViewHolder<T>> {
                override fun invoke(viewHolder: RecyclerViewHolder<T>, position: Int, item: T) =
                    it(viewHolder as VH, position, item as P)
            }

            registerViewHolderBoundListener(itemType, onBindViewHolderListener)
        }

        onBindViewHolderWithPayload?.let {
            val onBindViewHolderWithPayloadListener =
                object : OnBindViewHolderWithPayload<T, RecyclerViewHolder<T>> {
                    override fun invoke(
                        viewHolder: RecyclerViewHolder<T>,
                        position: Int,
                        item: T,
                        payloads: List<Any>
                    ) = it(viewHolder as VH, position, item as P, payloads)
                }

            registerViewHolderBoundWithPayloadListener(
                itemType,
                onBindViewHolderWithPayloadListener
            )
        }
    }

    abstract fun create(): A
}