package me.ibrahimyilmaz.kiel.samples.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Poll

class PollMessageViewHolder(view: View) : RecyclerViewHolder<Poll>(view) {
    val pollTitle = view.findViewById<TextView>(R.id.pollTitle)
    val sentAt = view.findViewById<TextView>(R.id.sentAt)
    val pollOptionsRecyclerView =
        view.findViewById<RecyclerView>(R.id.pollOptionsRecyclerView)
}