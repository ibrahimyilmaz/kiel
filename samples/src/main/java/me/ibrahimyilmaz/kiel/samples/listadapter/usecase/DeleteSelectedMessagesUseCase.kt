package me.ibrahimyilmaz.kiel.samples.listadapter.usecase

import me.ibrahimyilmaz.kiel.samples.listadapter.MessageRepository

class DeleteSelectedMessagesUseCase(
    private val repository: MessageRepository,
    private val mapper: MessageListItemViewStateMapper
) {

    operator fun invoke() = repository.deleteSelectedMessages().map(mapper::map)
}
