package me.ibrahimyilmaz.kiel.samples.listadapter.usecase

import me.ibrahimyilmaz.kiel.samples.listadapter.model.Message
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageSelectionState

class MessageListItemViewStateMapper {
    fun map(
        entry: Map.Entry<Message, MessageSelectionState>
    ) = MessageListItemViewState(
        entry.key,
        entry.value
    )
}