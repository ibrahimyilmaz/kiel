package me.ibrahimyilmaz.kiel.samples.datasource.viewbinder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import me.ibrahimyilmaz.kiel.adapter.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Image

class ImageMessageViewHolder(view: View) : RecyclerViewHolder<Image>(view) {
    val messageImage = view.findViewById<ImageView>(R.id.messageImage)
    val messageText = view.findViewById<TextView>(R.id.messageText)
    val sentAt = view.findViewById<TextView>(R.id.sentAt)
}