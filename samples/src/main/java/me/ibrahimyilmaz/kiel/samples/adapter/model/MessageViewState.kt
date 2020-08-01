package me.ibrahimyilmaz.kiel.samples.adapter.model

sealed class MessageViewState {

    data class Poll(
        val text: String,
        val options: List<PollOption>,
        val sentAt: String
    ) : MessageViewState() {
        data class PollOption(val text: String)
    }

    data class Text(
        val text: String,
        val sentAt: String
    ) : MessageViewState()

    data class Image(
        val text: String,
        val imageUrl: String,
        val sentAt: String
    ) : MessageViewState()
}