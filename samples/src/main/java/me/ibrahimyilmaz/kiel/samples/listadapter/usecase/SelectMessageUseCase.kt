package me.ibrahimyilmaz.kiel.samples.listadapter.usecase

import me.ibrahimyilmaz.kiel.samples.listadapter.MessageRepository
import me.ibrahimyilmaz.kiel.samples.listadapter.model.Message
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState

class SelectMessageUseCase(
    private val repository: MessageRepository,
    private val mapper: MessageListItemViewStateMapper
) {

    operator fun invoke(
        message: Message
    ) = repository.selectMessage(message).map(mapper::map)
}