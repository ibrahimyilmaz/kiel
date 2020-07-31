package me.ibrahimyilmaz.kiel.datasource

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import me.ibrahimyilmaz.kiel.adapter.RecyclerPagerAdapter
import me.ibrahimyilmaz.kiel.renderer.Renderer

class RecyclerPagerDataSource<T : Any>(
    renderers: List<Renderer<T>>
) : DataSource<T, RecyclerPagerAdapter<T>>(renderers) {

    fun getItemViewType(
        data: T
    ) = requireNotNull(rendererMap[data.javaClass]?.itemViewType)

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