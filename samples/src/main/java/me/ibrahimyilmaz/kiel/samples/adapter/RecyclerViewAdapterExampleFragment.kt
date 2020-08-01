package me.ibrahimyilmaz.kiel.samples.adapter

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ibrahimyilmaz.kiel.adapter.adaptersOf
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Text
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Image
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Poll
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Poll.PollOption
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.ImageMessageViewHolder
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.PollMessageViewHolder
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.PollOptionViewHolder
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.TextMessageViewHolder
import me.ibrahimyilmaz.kiel.samples.utils.MarginItemDecoration

class RecyclerViewAdapterExampleFragment : Fragment(R.layout.fragment_recyclerviewadapter_example) {

    private lateinit var messagesRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView)

        val recyclerViewAdapter =
            adaptersOf<MessageViewState> {
                register {
                    type { Text::class.java }
                    layoutResource { R.layout.adapter_message_text_item }
                    viewHolder { ::TextMessageViewHolder }
                    onViewHolderBound<Text, TextMessageViewHolder> { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                }

                register {
                    type { Image::class.java }
                    layoutResource { R.layout.adapter_message_image_item }
                    viewHolder { ::ImageMessageViewHolder }
                    onViewHolderBound<Image, ImageMessageViewHolder> { vh, i, item ->
                        vh.messageText.text = item.text
                        vh.sentAt.text = item.sentAt

                        Glide.with(vh.messageImage)
                            .load(item.imageUrl)
                            .into(vh.messageImage)
                    }
                }

                register {
                    type { Poll::class.java }
                    layoutResource { R.layout.adapter_message_poll_item }
                    viewHolder { ::PollMessageViewHolder }

                    onViewHolderCreated<PollMessageViewHolder> {
                        Log.d("TEST!", "onViewHolderCreated")
                    }

                    onViewHolderBound<Poll, PollMessageViewHolder> { viewHolder, position, poll ->
                        viewHolder.pollTitle.text = poll.text
                        viewHolder.sentAt.text = poll.sentAt

                        val pollOptionsAdapter =
                            adaptersOf<PollOption> {
                                register {
                                    type { PollOption::class.java }
                                    layoutResource { R.layout.adapter_poll_option_item }
                                    viewHolder { ::PollOptionViewHolder }
                                    onViewHolderBound<PollOption, PollOptionViewHolder> { vh, _, item ->
                                        vh.pollOption.text = item.text
                                    }
                                }
                            }

                        viewHolder.pollOptionsRecyclerView.adapter = pollOptionsAdapter

                        pollOptionsAdapter.setData(poll.options)
                    }
                }
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
        recyclerViewAdapter.setData(data)
    }
}