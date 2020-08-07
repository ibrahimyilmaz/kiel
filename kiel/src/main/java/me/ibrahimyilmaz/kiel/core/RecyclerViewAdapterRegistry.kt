package me.ibrahimyilmaz.kiel.core

import androidx.annotation.LayoutRes

data class RecyclerViewAdapterRegistry<T : Any, VH : RecyclerViewHolder<T>>(
    val type: Class<*>,
    val viewHolderFactory: ViewHolderFactory<T, RecyclerViewHolder<T>>,
    @LayoutRes
    val layoutResource: Int,
    val onViewHolderCreated: OnViewHolderCreated<VH>? = null,
    val onViewHolderBound: OnViewHolderBound<T, VH>? = null,
    val onViewHolderBoundWithPayload: OnViewHolderBoundWithPayload<T, VH>? = null
)
