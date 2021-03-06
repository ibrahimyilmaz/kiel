package me.ibrahimyilmaz.kiel.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.ibrahimyilmaz.kiel.core.AdapterFactory
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager

class RecyclerViewAdapterFactory<T : Any> :
    AdapterFactory<T, ListAdapter<T, RecyclerViewHolder<T>>> {

    override fun create(
        recyclerViewHolderManager: RecyclerViewHolderManager<T, RecyclerViewHolder<T>>,
        itemDiffUtil: DiffUtil.ItemCallback<T>?
    ) = RecyclerViewAdapter(recyclerViewHolderManager, itemDiffUtil)
}