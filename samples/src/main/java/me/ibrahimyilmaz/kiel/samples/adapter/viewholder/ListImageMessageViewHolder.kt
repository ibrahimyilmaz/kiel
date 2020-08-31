package me.ibrahimyilmaz.kiel.samples.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.ListImage

class ListImageMessageViewHolder(view: View) : RecyclerViewHolder<ListImage>(view) {
    val listTitle = view.findViewById<TextView>(R.id.listTitle)
    val sentAt = view.findViewById<TextView>(R.id.sentAt)
    val listRecyclerView =
        view.findViewById<RecyclerView>(R.id.listRecyclerView)
}
