package me.ibrahimyilmaz.kiel.core

import androidx.annotation.LayoutRes

@Deprecated(
    "It belongs to AdapterRegistryBuilder," +
        " when AdapterRegistryBuilder is removed, this one also"
)
data class RecyclerViewAdapterRegistry<T : Any, VH : RecyclerViewHolder<T>>(
    val type: Class<*>,
    val viewHolderFactory: ViewHolderFactory<T, RecyclerViewHolder<T>>,
    @LayoutRes
    val layoutResource: Int,
    val onViewHolderCreated: OnViewHolderCreated<VH>? = null,
    val onBindViewHolder: OnBindViewHolder<T, VH>? = null,
    val onViewHolderBoundWithPayload: OnBindViewHolderWithPayload<T, VH>? = null
)
