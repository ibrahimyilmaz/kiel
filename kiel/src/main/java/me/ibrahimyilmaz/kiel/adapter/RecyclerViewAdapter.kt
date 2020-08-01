package me.ibrahimyilmaz.kiel.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.collection.SimpleArrayMap
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KFunction1

class RecyclerViewAdapter<T : Any, VH : RecyclerViewHolder<T>>(
    private val recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
    private val diffCallbackFactory: RecyclerDiffCallbackFactory<T> = RecyclerDiffCallbackFactoryImpl()
) : RecyclerView.Adapter<VH>() {

    private val items = mutableListOf<T>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH = recyclerViewHolderManager.instantiate(parent, viewType)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) = recyclerViewHolderManager.onBindViewHolder(
        holder,
        position,
        items[position]
    )

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        payloads: MutableList<Any>
    ) = recyclerViewHolderManager.onBindViewHolder(
        holder,
        position,
        items[position],
        payloads
    )

    override fun getItemViewType(
        position: Int
    ) = recyclerViewHolderManager.getItemViewType(items[position])

    fun setData(data: List<T>) {
        val calculateDiff = DiffUtil.calculateDiff(diffCallbackFactory.create(items, data))
        items.clear()
        items += data
        calculateDiff.dispatchUpdatesTo(this)
    }
}

typealias OnViewHolderCreated<VH> = (VH) -> Unit
typealias OnViewHolderBound<T, VH> = (VH, Int, T) -> Unit
typealias OnViewHolderBoundWithPayload<T, VH> = (VH, Int, T, List<Any>) -> Unit

data class RecyclerViewAdapterRegistry<T : Any, VH : RecyclerViewHolder<T>>(
    val type: Class<*>,
    val viewHolderIntrospection: KFunction1<View, RecyclerViewHolder<T>>,
    @LayoutRes
    val layoutResource: Int,
    val onViewHolderCreated: OnViewHolderCreated<VH>? = null,
    val onViewHolderBound: OnViewHolderBound<T, VH>? = null,
    val onViewHolderBoundWithPayload: OnViewHolderBoundWithPayload<T, VH>? = null
)

class RecyclerViewAdapterRegistryBuilder<T : Any> {
    private var type: Class<*>? = null
    private var viewHolderIntrospection: KFunction1<View, RecyclerViewHolder<T>>? = null

    @LayoutRes
    private var layoutResource: Int? = null

    private var onViewHolderCreated: OnViewHolderCreated<RecyclerViewHolder<T>>? = null
    private var onViewHolderBound: OnViewHolderBound<T, RecyclerViewHolder<T>>? = null
    private var onViewHolderBoundWithPayload: OnViewHolderBoundWithPayload<T, RecyclerViewHolder<T>>? =
        null

    fun type(lambda: () -> Class<*>) {
        this.type = lambda()
    }

    fun viewHolder(lambda: () -> KFunction1<View, RecyclerViewHolder<T>>) {
        this.viewHolderIntrospection = lambda()
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
        requireNotNull(type),
        requireNotNull(viewHolderIntrospection),
        requireNotNull(layoutResource),
        onViewHolderCreated,
        onViewHolderBound,
        onViewHolderBoundWithPayload
    )

}

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

    fun register(
        lambda: RecyclerViewAdapterRegistryBuilder<T>.() -> Unit
    ) {
        with(
            RecyclerViewAdapterRegistryBuilder<T>()
                .apply(lambda).build()) {
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
            )
        )
}

fun <T : Any> adaptersOf(
    function: RecyclerViewAdapterBuilder<T>.() -> Unit
): RecyclerViewAdapter<T, RecyclerViewHolder<T>> =
    RecyclerViewAdapterBuilder<T>().apply(function).build()
