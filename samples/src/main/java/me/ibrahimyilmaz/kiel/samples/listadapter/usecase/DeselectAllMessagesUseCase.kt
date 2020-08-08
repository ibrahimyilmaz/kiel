package me.ibrahimyilmaz.kiel.samples.listadapter.usecase

import me.ibrahimyilmaz.kiel.samples.listadapter.MessageRepository

class DeselectAllMessagesUseCase(
    private val repository: MessageRepository,
    private val mapper: MessageListItemViewStateMapper
) {

    operator fun invoke() = repository.deselectAllMessages().map(mapper::map)
}
