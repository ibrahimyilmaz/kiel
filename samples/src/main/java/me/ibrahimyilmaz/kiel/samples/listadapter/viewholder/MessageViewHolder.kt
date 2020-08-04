package me.ibrahimyilmaz.kiel.samples.listadapter.viewholder

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.databinding.AdapterMessageListItemBinding
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageSelectionState

class MessageViewHolder(view: View) :
    RecyclerViewHolder<MessageListItemViewState>(view) {
    private val binding = AdapterMessageListItemBinding.bind(view)
    var boundItem: MessageListItemViewState? = null

    override fun bind(position: Int, item: MessageListItemViewState) {
        boundItem = item

        binding.senderText.text = item.message.sender
        binding.contentText.text = item.message.content
        binding.imageLetter.text = item.message.sender.subSequence(0, 1)
        setSelectionState(item.selectionState)
    }

    override fun bind(position: Int, item: MessageListItemViewState, payloads: List<Any>) {
        val diffBundle = payloads.first() as? Bundle ?: return
        boundItem = item
        val messageSelectionState =
            diffBundle.getParcelable<MessageSelectionState>(KEY_SELECTION) ?: return

        setSelectionState(messageSelectionState)
    }

    private fun setSelectionState(messageSelectionState: MessageSelectionState) {
        itemView.isSelected = messageSelectionState == MessageSelectionState.Selected
        binding.selectedImage.isVisible = itemView.isSelected
        binding.imageLetter.isVisible = !itemView.isSelected
    }

    companion object {
        const val KEY_SELECTION = "KEY_SELECTION"
    }
}