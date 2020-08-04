package me.ibrahimyilmaz.kiel.samples.listadapter.usecase

import me.ibrahimyilmaz.kiel.samples.listadapter.MessageRepository
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageSelectionState

class FetchMessagesUseCase(
    private val repository: MessageRepository,
    private val mapper: MessageListItemViewStateMapper
) {

    suspend operator fun invoke() = repository.getMessages().map(mapper::map)
}