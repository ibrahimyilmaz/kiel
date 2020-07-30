package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import me.ibrahimyilmaz.kiel.datasource.RecyclerDataSource

class RecyclerAdapter<T : Any>(
    private val dataSource: RecyclerDataSource<T>,
    private val itemIdProvider: ItemIdProvider<T>? = null
) : Adapter<RecyclerViewHolder<T>>() {

    init {
        dataSource.attachToAdapter(this)
        setHasStableIds(itemIdProvider != null)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RecyclerViewHolder(parent, dataSource.getRendererOf(viewType))

    override fun getItemCount() = dataSource.count

    override fun onBindViewHolder(holder: RecyclerViewHolder<T>, position: Int) =
        holder.bind(dataSource[position])

    override fun onBindViewHolder(
        holder: RecyclerViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.bind(dataSource[position], payloads)
    }

    override fun getItemViewType(position: Int) = dataSource.getItemViewType(position)

    override fun getItemId(position: Int) =
        itemIdProvider?.provideId(dataSource[position]) ?: super.getItemId(position)

    companion object {

        operator fun <T : Any> invoke(
            dataSource: RecyclerDataSource<T>,
            itemIdProvider: ItemIdProviderOperator<T>? = null
        ) = RecyclerAdapter(
            dataSource,
            itemIdProvider?.let {
                object : ItemIdProvider<T> {
                    override fun provideId(item: T): Long = it(item)
                }
            }
        )
    }
}