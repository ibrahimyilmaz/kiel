package me.ibrahimyilmaz.kiel.core

import android.view.View
import androidx.annotation.LayoutRes

@Deprecated(
    "In general we try to avoid builder classes in Kotlin," +
        " as they're not needed and introduce nullability." +
        " So AdapterRegistryBuilder will be retired soon"
)
class AdapterRegistryBuilder<T : Any> {
    private var type: Class<*>? = null
    private var viewHolderIntrospection: ViewHolderFactory<T, RecyclerViewHolder<T>>? = null

    @LayoutRes
    private var layoutResource: Int? = null

    private var onViewHolderCreated: OnViewHolderCreated<RecyclerViewHolder<T>>? = null
    private var onViewHolderBound: OnBindViewHolder<T, RecyclerViewHolder<T>>? = null
    private var onViewHolderBoundWithPayload:
        OnBindViewHolderWithPayload<T, RecyclerViewHolder<T>>? = null

    fun type(lambda: () -> Class<*>) {
        this.type = lambda()
    }

    fun viewHolder(lambda: ViewHolderCreator<RecyclerViewHolder<T>>) {
        this.viewHolderIntrospection = object : ViewHolderFactory<T, RecyclerViewHolder<T>> {
            override fun instantiate(view: View): RecyclerViewHolder<T> = lambda(view)
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
            OnBindViewHolder<T, RecyclerViewHolder<T>> {
            @Suppress("UNCHECKED_CAST")
            override fun invoke(viewHolder: RecyclerViewHolder<T>, position: Int, item: T) {
                lambda(viewHolder as VH, position, item as IT)
            }
        }
    }

    fun <IT, VH : RecyclerViewHolder<T>> onViewHolderBoundWithPayload(
        lambda: (VH, Int, IT, List<Any>) -> Unit
    ) {
        onViewHolderBoundWithPayload =
            object :
                OnBindViewHolderWithPayload<T, RecyclerViewHolder<T>> {
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
        requireNotNull(type) {
            "type should be provided!"
        },
        requireNotNull(viewHolderIntrospection) {
            "viewHolder constructor method should be provided!"
        },
        requireNotNull(layoutResource) {
            "layout resource should be provided!"
        },
        onViewHolderCreated,
        onViewHolderBound,
        onViewHolderBoundWithPayload
    )
}
