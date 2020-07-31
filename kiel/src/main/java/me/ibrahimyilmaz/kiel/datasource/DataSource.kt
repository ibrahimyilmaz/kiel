package me.ibrahimyilmaz.kiel.datasource

import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.renderer.Renderer
import java.lang.ref.WeakReference

abstract class DataSource<T : Any, A : RecyclerView.Adapter<*>>(
    renderers: List<Renderer<T>>
) {
    private val viewTypeToRendererKeyMap = renderers
        .map {
            it.itemViewType to it
        }.toMap()

    internal val rendererMap = renderers.map { it.key to it }.toMap()

    internal var recyclerViewAdapter = WeakReference<A>(null)

    fun attachToAdapter(adapter: A) {
        recyclerViewAdapter = WeakReference(adapter)
    }

    fun getRendererOf(
        viewType: Int
    ) = requireNotNull(viewTypeToRendererKeyMap[viewType])
}

