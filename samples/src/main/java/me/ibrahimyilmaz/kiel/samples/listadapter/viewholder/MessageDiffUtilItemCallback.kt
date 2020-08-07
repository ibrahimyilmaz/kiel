package me.ibrahimyilmaz.kiel.samples.listadapter.viewholder

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.viewholder.MessageViewHolder.Companion.KEY_SELECTION

class MessageDiffUtilItemCallback : DiffUtil.ItemCallback<MessageListItemViewState>() {
    override fun areItemsTheSame(
        oldItem: MessageListItemViewState,
        newItem: MessageListItemViewState
    ) = oldItem.message.id == newItem.message.id

    override fun areContentsTheSame(
        oldItem: MessageListItemViewState,
        newItem: MessageListItemViewState
    ) = oldItem == newItem

    override fun getChangePayload(
        oldItem: MessageListItemViewState,
        newItem: MessageListItemViewState
    ): Any? {
        val diffBundle = Bundle()

        if (oldItem.selectionState != newItem.selectionState) {
            diffBundle.putParcelable(
                KEY_SELECTION,
                newItem.selectionState
            )
        }

        return if (diffBundle.isEmpty) super.getChangePayload(
            oldItem,
            newItem
        ) else diffBundle
    }
}