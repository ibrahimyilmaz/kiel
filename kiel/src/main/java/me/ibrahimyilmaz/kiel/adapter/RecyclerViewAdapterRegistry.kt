package me.ibrahimyilmaz.kiel.adapter

import android.view.View
import androidx.annotation.LayoutRes
import kotlin.reflect.KFunction1

data class RecyclerViewAdapterRegistry<T : Any, VH : RecyclerViewHolder<T>>(
    val type: Class<*>,
    val viewHolderIntrospection: KFunction1<View, RecyclerViewHolder<T>>,
    @LayoutRes
    val layoutResource: Int,
    val onViewHolderCreated: OnViewHolderCreated<VH>? = null,
    val onViewHolderBound: OnViewHolderBound<T, VH>? = null,
    val onViewHolderBoundWithPayload: OnViewHolderBoundWithPayload<T, VH>? = null
)
