package me.ibrahimyilmaz.kiel.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import me.ibrahimyilmaz.kiel.datasource.RecyclerPagerDataSource

class RecyclerPagerAdapter<T : Any>(
    private val dataSource: RecyclerPagerDataSource<T>,
    diffUtilItemCallBack: DiffUtil.ItemCallback<T> = RecyclerDiffItemCallback()
) : PagingDataAdapter<T, RecyclerViewHolder<T>>(diffUtilItemCallBack) {

    init {
        dataSource.attachToAdapter(this)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<T>, position: Int) {
        holder.bind(checkNotNull(getItem(position)))
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.bind(checkNotNull(getItem(position)), payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<T> =
        RecyclerViewHolder(
            parent,
            dataSource.getRendererOf(viewType)
        )

    override fun getItemViewType(position: Int) =
        dataSource.getItemViewType(checkNotNull(getItem(position)))
}