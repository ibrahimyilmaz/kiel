package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

fun <T : Any> adaptersOf(
    function: RecyclerViewAdapterBuilder<T>.() -> Unit
): RecyclerViewAdapter<T, RecyclerViewHolder<T>> =
    RecyclerViewAdapterBuilder<T>().apply(function).build()

class RecyclerViewAdapter<T : Any, VH : RecyclerViewHolder<T>>(
    private val recyclerViewHolderManager: RecyclerViewHolderManager<T, VH>,
    private val diffCallbackFactory: RecyclerDiffCallbackFactory<T> = RecyclerDiffCallbackFactoryImpl()
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

    fun setData(data: List<T>) {
        val calculateDiff = DiffUtil.calculateDiff(diffCallbackFactory.create(items, data))
        items.clear()
        items += data
        calculateDiff.dispatchUpdatesTo(this)
    }
}
