package me.ibrahimyilmaz.kiel.samples.datasource.viewbinder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import me.ibrahimyilmaz.kiel.binder.ViewBinder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Image

class ImageMessageViewBinder(view: View) : ViewBinder<Image>(view) {
    private val messageImage = view.findViewById<ImageView>(R.id.messageImage)
    private val messageText = view.findViewById<TextView>(R.id.messageText)
    private val sentAt = view.findViewById<TextView>(R.id.sentAt)

    override fun bind(item: Image) {
        messageText.text = item.text
        messageText.text = item.text
        sentAt.text = item.sentAt

        Glide.with(messageImage)
            .load(item.imageUrl)
            .into(messageImage)
    }
}