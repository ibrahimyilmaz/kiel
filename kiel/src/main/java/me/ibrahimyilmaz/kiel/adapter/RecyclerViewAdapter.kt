package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolderManager

class RecyclerViewAdapter<T : Any, VH : RecyclerViewHolder<T>> private constructor(
    private val recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
    private val diffUtilCallbackFactory: RecyclerDiffUtilCallbackFactory<T> = RecyclerDiffUtilCallbackFactoryImpl()
) : RecyclerView.Adapter<VH>() {

    private val items = mutableListOf<T>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH = recyclerViewHolderManager.instantiate(parent, viewType)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) = recyclerViewHolderManager.onBindViewHolder(
        holder,
        position,
        items[position]
    )

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        payloads: MutableList<Any>
    ) = recyclerViewHolderManager.onBindViewHolder(
        holder,
        position,
        items[position],
        payloads
    )

    override fun getItemViewType(
        position: Int
    ) = recyclerViewHolderManager.getItemViewType(items[position])

    @MainThread
    fun setData(data: List<T>) {
        val calculateDiff = DiffUtil.calculateDiff(diffUtilCallbackFactory.create(items, data))
        items.clear()
        items += data
        calculateDiff.dispatchUpdatesTo(this)
    }

    companion object {

        @JvmStatic
        operator fun <T : Any, VH : RecyclerViewHolder<T>> invoke(
            recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
            diffUtilCallbackFactory: RecyclerDiffUtilCallbackFactory<T>?
        ) = diffUtilCallbackFactory?.let {
            RecyclerViewAdapter(recyclerViewHolderManager, it)
        } ?: RecyclerViewAdapter(recyclerViewHolderManager)

        @JvmStatic
        inline fun <T : Any> adapterOf(
            function: RecyclerViewAdapterBuilder<T>.() -> Unit
        ): RecyclerViewAdapter<T, RecyclerViewHolder<T>> =
            RecyclerViewAdapterBuilder<T>().apply(function).build()
    }
}
