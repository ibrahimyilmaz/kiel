package me.ibrahimyilmaz.kiel.pageradapter

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import me.ibrahimyilmaz.kiel.core.AdapterFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager

class RecyclerViewPagingDataAdapterFactory<T : Any> :
    AdapterFactory<T, PagingDataAdapter<T, RecyclerViewHolder<T>>> {

    override fun create(
        recyclerViewHolderManager: RecyclerViewHolderManager<T, RecyclerViewHolder<T>>,
        itemDiffUtil: DiffUtil.ItemCallback<T>?
    ) = RecyclerViewPagingDataAdapter(recyclerViewHolderManager, itemDiffUtil)
}