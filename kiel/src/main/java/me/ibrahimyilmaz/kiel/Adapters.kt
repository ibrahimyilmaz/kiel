@file:kotlin.jvm.JvmName("AdaptersKt")
@file:Suppress("UNCHECKED_CAST")

package me.ibrahimyilmaz.kiel

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import me.ibrahimyilmaz.kiel.adapter.RecyclerViewAdapterFactory
import me.ibrahimyilmaz.kiel.core.AdapterBuilder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.pageradapter.RecyclerViewPagingDataAdapterFactory

public inline fun <T : Any> adapterOf(
    adapterBuilder: AdapterBuilder<T>.() -> Unit
): ListAdapter<T, RecyclerViewHolder<T>> =
    AdapterBuilder<T>().apply(adapterBuilder).build(
        RecyclerViewAdapterFactory()
    ) as ListAdapter<T, RecyclerViewHolder<T>>

public inline fun <T : Any> pagingDataAdapterOf(
    adapterBuilder: AdapterBuilder<T>.() -> Unit
): PagingDataAdapter<T, RecyclerViewHolder<T>> =
    AdapterBuilder<T>().apply(adapterBuilder).build(
        RecyclerViewPagingDataAdapterFactory()
    ) as PagingDataAdapter<T, RecyclerViewHolder<T>>