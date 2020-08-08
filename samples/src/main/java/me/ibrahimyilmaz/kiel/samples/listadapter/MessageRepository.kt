package me.ibrahimyilmaz.kiel.samples.listadapter

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.ibrahimyilmaz.kiel.samples.listadapter.model.Message
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageSelectionState

class MessageRepository {

    private var messages = mutableMapOf<Message, MessageSelectionState>()

    suspend fun getMessages(): Map<Message, MessageSelectionState> = withContext(IO) {
        if (messages.isEmpty()) {
            delay(500)
            (0..SAMPLE_MESSAGE_COUNT).forEach {
                val message = Message(
                    it.toLong(),
                    SENDERS[it % SENDERS.size],
                    TITLE[it % TITLE.size],
                    CONTENT[it % CONTENT.size]
                )
                messages[message] = MessageSelectionState.Normal
            }
        }

        messages
    }

    fun selectMessage(message: Message): Map<Message, MessageSelectionState> {
        messages[message] = MessageSelectionState.Selected
        return messages
    }

    fun deselectMessage(message: Message): Map<Message, MessageSelectionState> {
        messages[message] = MessageSelectionState.Normal
        return messages
    }

    fun deleteSelectedMessages(): Map<Message, MessageSelectionState> {
        val selectedMessages =
            messages.asSequence().filter { it.value == MessageSelectionState.Selected }
                .map { it.key }.toList()
        selectedMessages.forEach {
            messages.remove(it)
        }
        return messages
    }

    fun deselectAllMessages(): Map<Message, MessageSelectionState> {
        messages.keys.forEach {
            messages[it] = MessageSelectionState.Normal
        }
        return messages
    }

    private companion object {
        private const val SAMPLE_MESSAGE_COUNT = 10
        private val SENDERS =
            arrayOf("Christian Dalonzo", "Amy Worrall", "Brendan Aronoff", "Hailey Mustermann")
        private val TITLE = arrayOf(
            "Re: Something1",
            "Birthday Party",
            "Say YES to Liverpool Game",
            "Gigi has invited you to work"
        )
        private val CONTENT = arrayOf(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            "Hodor. Hodor hodor, hodor. Hodor hodor hodor hodor hodor. Hodor. Hodor! Hodor hodor, hodor; hodor hodor hodor. Hodor. Hodor",
            "Lorem Ipsum is the single greatest threat. We are not - we are not keeping up with other websites.",
            "Lorem ipsum dolor amet mustache knausgaard +1, blue bottle waistcoat tbh semiotics artisan synth stumptown gastropub cornhole celiac swag",
            "Zombie ipsum reversus ab viral inferno, nam rick grimes malum cerebro."
        )
    }
}
