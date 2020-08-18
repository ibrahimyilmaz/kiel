package me.ibrahimyilmaz.kiel.core

import android.view.View
import androidx.annotation.LayoutRes
import androidx.collection.SimpleArrayMap
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

typealias AreItemsTheSame<T> = (old: T, new: T) -> Boolean
typealias AreContentsTheSame<T> = (old: T, new: T) -> Boolean
typealias GetChangePayload<T> = (oldItem: T, newItem: T) -> Any?

class AdapterBuilder<T : Any> {

    private var itemDiffUtil: DiffUtil.ItemCallback<T>? = null

    private val viewHolderMap =
        SimpleArrayMap<Int, ViewHolderFactory<T, RecyclerViewHolder<T>>>()

    private val layoutResourceMap =
        SimpleArrayMap<Class<*>, Int>()

    private val viewHolderCreatedListeners =
        SimpleArrayMap<Int, OnViewHolderCreated<RecyclerViewHolder<T>>>()

    private val onBindViewHolderListeners =
        SimpleArrayMap<Class<*>, OnBindViewHolder<T, RecyclerViewHolder<T>>>()

    private val onBindViewHolderWithPayloadListeners =
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

    fun build(
        factory: AdapterFactory<T, RecyclerView.Adapter<RecyclerViewHolder<T>>>
    ) = factory.create(
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