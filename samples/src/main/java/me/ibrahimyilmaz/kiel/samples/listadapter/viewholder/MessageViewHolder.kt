package me.ibrahimyilmaz.kiel.samples.listadapter.viewholder

import android.view.View
import androidx.core.view.isVisible
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.databinding.AdapterMessageListItemBinding
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageSelectionState

class MessageViewHolder(view: View) :
    RecyclerViewHolder<MessageListItemViewState>(view) {
    val binding = AdapterMessageListItemBinding.bind(view)
    var boundItem: MessageListItemViewState? = null

    override fun bind(position: Int, item: MessageListItemViewState) {
        boundItem = item
    }

    override fun bind(position: Int, item: MessageListItemViewState, payloads: List<Any>) {
        boundItem = item
    }

    fun setSelectionState(messageSelectionState: MessageSelectionState) {
        itemView.isSelected = messageSelectionState == MessageSelectionState.Selected
        binding.selectedImage.isVisible = itemView.isSelected
        binding.imageLetter.isVisible = !itemView.isSelected
    }
}