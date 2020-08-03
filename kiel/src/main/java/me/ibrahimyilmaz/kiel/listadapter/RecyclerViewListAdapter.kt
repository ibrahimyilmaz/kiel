package me.ibrahimyilmaz.kiel.listadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager

class RecyclerViewListAdapter<T : Any, VH : RecyclerViewHolder<T>>(
    private val recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
    diffUtilItemCallback: ItemCallback<T> = RecyclerViewItemCallback()
) : ListAdapter<T, VH>(diffUtilItemCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH = recyclerViewHolderManager.instantiate(
        parent,
        viewType
    )

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

    companion object {

        @JvmStatic
        operator fun <T : Any, VH : RecyclerViewHolder<T>> invoke(
            recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
            diffUtilCallbackFactory: ItemCallback<T>?
        ) = diffUtilCallbackFactory?.let {
            RecyclerViewListAdapter(recyclerViewHolderManager, it)
        } ?: RecyclerViewListAdapter(recyclerViewHolderManager)

        @JvmStatic
        inline fun <T : Any> listAdapterOf(
            function: RecyclerViewListAdapterBuilder<T>.() -> Unit
        ): RecyclerViewListAdapter<T, RecyclerViewHolder<T>> =
            RecyclerViewListAdapterBuilder<T>().apply(function).build()
    }
}