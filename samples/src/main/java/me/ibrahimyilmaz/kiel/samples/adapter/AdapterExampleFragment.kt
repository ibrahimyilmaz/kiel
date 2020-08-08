package me.ibrahimyilmaz.kiel.samples.adapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ibrahimyilmaz.kiel.adapter.RecyclerViewAdapter.Companion.adapterOf
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Image
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Poll
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Poll.PollOption
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Text
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.ImageMessageViewHolder
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.PollMessageViewHolder
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.PollOptionViewHolder
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.TextMessageViewHolder
import me.ibrahimyilmaz.kiel.samples.utils.MarginItemDecoration

class AdapterExampleFragment : Fragment(R.layout.fragment_adapter_example) {

    private lateinit var messagesRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView)

        val recyclerViewAdapter =
            adapterOf<MessageViewState> {
                register(
                    layoutResource = R.layout.adapter_message_text_item,
                    viewHolder = ::TextMessageViewHolder,
                    onBindBindViewHolder = { vh, _, text ->
                        vh.messageText.text = text.text
                        vh.sentAt.text = text.sentAt
                    }
                )

                register(
                    layoutResource = R.layout.adapter_message_image_item,
                    viewHolder = ::ImageMessageViewHolder,
                    onBindBindViewHolder = { vh, _, item ->
                        vh.messageText.text = item.text
                        vh.sentAt.text = item.sentAt

                        Glide.with(vh.messageImage)
                            .load(item.imageUrl)
                            .into(vh.messageImage)
                    }
                )

                register(
                    layoutResource = R.layout.adapter_message_poll_item,
                    viewHolder = ::PollMessageViewHolder,
                    onBindBindViewHolder = { vh, _, poll ->
                        vh.pollTitle.text = poll.text
                        vh.sentAt.text = poll.sentAt

                        val pollOptionsAdapter = adapterOf<PollOption> {
                            register(
                                layoutResource = R.layout.adapter_poll_option_item,
                                viewHolder = ::PollOptionViewHolder,
                                onBindBindViewHolder = { pollOptionViewHolder, _, pollOption ->
                                    pollOptionViewHolder.pollOption.text = pollOption.text
                                }
                            )
                        }

                        vh.pollOptionsRecyclerView.adapter = pollOptionsAdapter

                        pollOptionsAdapter.submitList(poll.options)
                    }
                )
            }

        messagesRecyclerView.adapter =
            recyclerViewAdapter

        messagesRecyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.padding_normal).toInt()
            )
        )
        val data = listOf(
            Text(
                "Hi David! I have landed to Earth! Human-kind is really interesting",
                "09:10 AM"
            ),
            Image(
                "Looks amazing, isn't it?",
                "http://lorempixel.com/400/400/nature/",
                "09:10 AM"
            ),
            Poll(
                "Where should I have my dinner?",
                listOf(
                    PollOption("La Paradate - Barcelona"),
                    PollOption("Ozzies Kokorec - Istanbul"),
                    PollOption("Cafe Pushkin - Moscow"),
                    PollOption("Gustarium - Florence"),
                    PollOption("Donerci Mustafa - Berlin")
                ),
                "15:15 PM"
            )
        )
        recyclerViewAdapter.submitList(data)
    }
}
