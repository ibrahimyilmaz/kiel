package me.ibrahimyilmaz.kiel.samples.datasource.viewbinder

import android.view.View
import android.widget.TextView
import me.ibrahimyilmaz.kiel.binder.ViewBinder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Text

class TextMessageViewBinder(view: View) : ViewBinder<Text>(view) {
    private val messageText = view.findViewById<TextView>(R.id.messageText)
    private val sentAt = view.findViewById<TextView>(R.id.sentAt)

    override fun bind(item: Text) {
        messageText.text = item.text
        sentAt.text = item.sentAt
    }
}