package me.ibrahimyilmaz.kiel.adapter

import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.item.Renderer
import java.lang.ref.WeakReference

class RecyclerDataSource<T : Any>(
    private val renderers: Map<Class<out T>, Renderer<T>>,
    private val diffCallbackFactory: RecyclerDiffCallbackFactory<T> = RecyclerDiffCallbackFactoryImpl()
) {

    private val viewTypeToRendererKeyMap = renderers
        .map {
            it.value.itemViewType to it.key
        }.toMap()

    private val _data = mutableListOf<T>()

    private var recyclerViewAdapter = WeakReference<RecyclerView.Adapter<*>>(null)

    fun getRendererOf(
        viewType: Int
    ) = requireNotNull(renderers[viewTypeToRendererKeyMap[viewType]])

    @LayoutRes
    fun getItemViewType(
        position: Int
    ) = requireNotNull(renderers[_data[position].javaClass]?.itemViewType)


    fun attachToAdapter(adapter: RecyclerView.Adapter<RecyclerViewHolder<T>>) {
        recyclerViewAdapter = WeakReference(adapter)
    }

    operator fun get(
        position: Int
    ): T = _data[position]

    val count: Int get() = _data.size

    @MainThread
    fun setData(
        value: List<T>
    ) {
        val calculateDiff = DiffUtil.calculateDiff(
            diffCallbackFactory.create(
                _data,
                value
            )
        )

        _data.clear()
        _data += value
        recyclerViewAdapter.get()?.let(calculateDiff::dispatchUpdatesTo)
    }

    @VisibleForTesting
    internal fun seedData(
        data: List<T>
    ) {
        _data.clear()
        _data += data
    }
}