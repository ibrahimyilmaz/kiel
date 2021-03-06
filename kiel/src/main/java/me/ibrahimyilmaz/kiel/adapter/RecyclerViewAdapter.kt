package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import me.ibrahimyilmaz.kiel.core.AdapterBuilder
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

        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        @Deprecated(
            "This function has been changed to be top level function." +
                "Please use top level version of adapterOf",
            ReplaceWith("me.ibrahimyilmaz.adapterOf"),
            DeprecationLevel.ERROR
        )
        inline fun <T : Any> adapterOf(
            adapterBuilder: AdapterBuilder<T>.() -> Unit
        ): ListAdapter<T, RecyclerViewHolder<T>> =
            AdapterBuilder<T>().apply(adapterBuilder).build(
                RecyclerViewAdapterFactory()
            ) as ListAdapter<T, RecyclerViewHolder<T>>
    }
}