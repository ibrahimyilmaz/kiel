package me.ibrahimyilmaz.kiel.adapter

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import me.ibrahimyilmaz.kiel.item.Renderer

class RecyclerPagerDataSource<T : Any>(
    private val renderers: Map<Class<out T>, Renderer<T>>
) : DataSource<T, RecyclerPagerAdapter<T>>(renderers) {

    fun getItemViewType(
        data: T
    ) = requireNotNull(renderers[data.javaClass]?.itemViewType)

    suspend fun submitData(pagingData: PagingData<T>) {
        recyclerViewAdapter.get()?.submitData(pagingData)
    }

    fun submitData(lifecycle: Lifecycle, pagingData: PagingData<T>) {
        recyclerViewAdapter.get()?.submitData(lifecycle, pagingData)
    }

    fun retry() {
        recyclerViewAdapter.get()?.retry()
    }

    fun refresh() {
        recyclerViewAdapter.get()?.refresh()
    }
}