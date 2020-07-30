package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import me.ibrahimyilmaz.kiel.datasource.RecyclerDataSource

class RecyclerAdapter<T : Any>(
    private val dataSource: RecyclerDataSource<T>
) : Adapter<RecyclerViewHolder<T>>() {

    init {
        dataSource.attachToAdapter(this)
        setHasStableIds(true)
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

    override fun getItemId(position: Int) = dataSource[position].hashCode().toLong()
}