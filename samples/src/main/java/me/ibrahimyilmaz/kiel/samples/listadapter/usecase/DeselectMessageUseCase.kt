package me.ibrahimyilmaz.kiel.samples.listadapter.usecase

import me.ibrahimyilmaz.kiel.samples.listadapter.MessageRepository
import me.ibrahimyilmaz.kiel.samples.listadapter.model.Message

class DeselectMessageUseCase(
    private val repository: MessageRepository,
    private val mapper: MessageListItemViewStateMapper
) {

    operator fun invoke(
        message: Message
    ) = repository.deselectMessage(message).map(mapper::map)
}
