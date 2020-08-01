package me.ibrahimyilmaz.kiel.adapter

import android.view.View
import androidx.annotation.LayoutRes
import kotlin.reflect.KFunction1


class RecyclerViewAdapterRegistryBuilder<T : Any> {
    private var type: Class<*>? = null
    private var viewHolderIntrospection: KFunction1<View, RecyclerViewHolder<T>>? = null

    @LayoutRes
    private var layoutResource: Int? = null

    private var onViewHolderCreated: OnViewHolderCreated<RecyclerViewHolder<T>>? = null
    private var onViewHolderBound: OnViewHolderBound<T, RecyclerViewHolder<T>>? = null
    private var onViewHolderBoundWithPayload: OnViewHolderBoundWithPayload<T, RecyclerViewHolder<T>>? =
        null

    private var diffUtilCallbackFactory: RecyclerDiffUtilCallbackFactory<T> =
        RecyclerDiffUtilCallbackFactoryImpl()

    fun type(lambda: () -> Class<*>) {
        this.type = lambda()
    }

    fun viewHolder(lambda: () -> KFunction1<View, RecyclerViewHolder<T>>) {
        this.viewHolderIntrospection = lambda()
    }

    fun diffUtilCallback(lambda: DiffUtilCallback<T>) {
        this.diffUtilCallbackFactory = object : RecyclerDiffUtilCallbackFactory<T> {
            override fun create(oldItems: List<T>, newItems: List<T>): RecyclerDiffUtilCallback<T> =
                lambda(oldItems, newItems)
        }
    }

    fun <VH : RecyclerViewHolder<T>> onViewHolderCreated(lambda: (VH) -> Unit) {
        onViewHolderCreated = object :
            OnViewHolderCreated<RecyclerViewHolder<T>> {
            @Suppress("UNCHECKED_CAST")
            override fun invoke(viewHolder: RecyclerViewHolder<T>) {
                lambda(viewHolder as VH)
            }
        }
    }

    fun <IT, VH : RecyclerViewHolder<IT>> onViewHolderBound(lambda: (VH, Int, IT) -> Unit) {
        onViewHolderBound = object :
            OnViewHolderBound<T, RecyclerViewHolder<T>> {
            @Suppress("UNCHECKED_CAST")
            override fun invoke(viewHolder: RecyclerViewHolder<T>, position: Int, item: T) {
                lambda(viewHolder as VH, position, item as IT)
            }
        }
    }

    fun <IT, VH : RecyclerViewHolder<T>> onViewHolderBoundWithPayload(lambda: (VH, Int, IT, List<Any>) -> Unit) {
        onViewHolderBoundWithPayload =
            object :
                OnViewHolderBoundWithPayload<T, RecyclerViewHolder<T>> {
                @Suppress("UNCHECKED_CAST")
                override fun invoke(
                    viewHolder: RecyclerViewHolder<T>,
                    position: Int,
                    item: T,
                    payloads: List<Any>
                ) {
                    lambda(viewHolder as VH, position, item as IT, payloads)
                }
            }
    }


    fun layoutResource(lambda: () -> Int) {
        this.layoutResource = lambda()
    }

    fun build() = RecyclerViewAdapterRegistry(
        requireNotNull(type) { "type should be provided!" },
        requireNotNull(viewHolderIntrospection) { "viewHolder constructor method should be provided!" },
        requireNotNull(layoutResource) { "layout resource should be provided!" },
        diffUtilCallbackFactory,
        onViewHolderCreated,
        onViewHolderBound,
        onViewHolderBoundWithPayload
    )

}
