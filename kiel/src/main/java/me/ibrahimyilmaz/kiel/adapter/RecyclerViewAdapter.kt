package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager
import me.ibrahimyilmaz.kiel.core.RecyclerViewItemCallback

class RecyclerViewAdapter<T : Any, VH : RecyclerViewHolder<T>>(
    private val recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
    diffUtilItemCallback: ItemCallback<T> = RecyclerViewItemCallback()
) : ListAdapter<T, VH>(diffUtilItemCallback) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH = recyclerViewHolderManager.instantiate(
        parent,
        viewType
    ) as VH

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) = recyclerViewHolderManager.onBindViewHolder(
        holder,
        position,
        getItem(position)
    )

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        payloads: MutableList<Any>
    ) = recyclerViewHolderManager.onBindViewHolder(
        holder,
        position,
        getItem(position),
        payloads
    )

    override fun getItemViewType(
        position: Int
    ) = recyclerViewHolderManager.getItemViewType(getItem(position))

    companion object {

        @JvmStatic
        operator fun <T : Any, VH : RecyclerViewHolder<T>> invoke(
            recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
            diffUtilCallbackFactory: ItemCallback<T>?
        ) = diffUtilCallbackFactory?.let {
            RecyclerViewAdapter(
                recyclerViewHolderManager,
                it
            )
        } ?: RecyclerViewAdapter(
            recyclerViewHolderManager
        )

        @JvmStatic
        inline fun <T : Any> adapterOf(
            function: RecyclerViewAdapterFactory<T>.() -> Unit
        ): RecyclerViewAdapter<T, RecyclerViewHolder<T>> =
            RecyclerViewAdapterFactory<T>()
                .apply(function).create()
    }
}