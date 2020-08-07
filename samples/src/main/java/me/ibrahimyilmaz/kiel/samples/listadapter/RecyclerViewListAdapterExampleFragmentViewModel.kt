package me.ibrahimyilmaz.kiel.samples.listadapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageSelectionState
import me.ibrahimyilmaz.kiel.samples.listadapter.usecase.DeleteSelectedMessagesUseCase
import me.ibrahimyilmaz.kiel.samples.listadapter.usecase.DeselectAllMessagesUseCase
import me.ibrahimyilmaz.kiel.samples.listadapter.usecase.DeselectMessageUseCase
import me.ibrahimyilmaz.kiel.samples.listadapter.usecase.FetchMessagesUseCase
import me.ibrahimyilmaz.kiel.samples.listadapter.usecase.MessageListItemViewStateMapper
import me.ibrahimyilmaz.kiel.samples.listadapter.usecase.SelectMessageUseCase

class RecyclerViewListAdapterExampleFragmentViewModel : ViewModel() {

    private val repository = MessageRepository()
    private val viewStateMapper = MessageListItemViewStateMapper()
    private val fetchMessagesUseCase = FetchMessagesUseCase(repository, viewStateMapper)
    private val selectMessageUseCase = SelectMessageUseCase(repository, viewStateMapper)
    private val deselectMessageUseCase = DeselectMessageUseCase(repository, viewStateMapper)
    private val deselectAllMessagesUseCase = DeselectAllMessagesUseCase(repository, viewStateMapper)

    private val deleteSelectedMessagesUseCase =
        DeleteSelectedMessagesUseCase(repository, viewStateMapper)

    private val _messages = MutableLiveData<List<MessageListItemViewState>>()

    val messages: LiveData<List<MessageListItemViewState>> get() = _messages

    val showSelectedMessageCount: LiveData<Int>
        get() = _messages.map {
            it.count { message ->
                message.selectionState == MessageSelectionState.Selected
            }
        }

    val showDeleteAction: LiveData<Boolean>
        get() = _messages.map {
            it.any { message ->
                message.selectionState == MessageSelectionState.Selected
            }
        }

    init {
        getMessages()
    }

    private fun getMessages() {
        viewModelScope.launch {
            _messages.value = fetchMessagesUseCase()
        }
    }

    fun onItemLongClicked(item: MessageListItemViewState) {
        _messages.value = when (item.selectionState) {
            MessageSelectionState.Selected -> deselectMessageUseCase(item.message)
            MessageSelectionState.Normal -> selectMessageUseCase(item.message)
        }
    }

    fun onDeleteActionClicked() {
        _messages.value = deleteSelectedMessagesUseCase()
    }

    fun onActionDeleteModeFinished() {
        _messages.value = deselectAllMessagesUseCase()
    }
}