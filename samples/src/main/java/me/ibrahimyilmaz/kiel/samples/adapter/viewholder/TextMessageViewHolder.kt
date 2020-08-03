package me.ibrahimyilmaz.kiel.samples.adapter.viewholder

import android.view.View
import android.widget.TextView
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Text


class TextMessageViewHolder(view: View) : RecyclerViewHolder<Text>(view) {
    val messageText = view.findViewById<TextView>(R.id.messageText)
    val sentAt = view.findViewById<TextView>(R.id.sentAt)
}