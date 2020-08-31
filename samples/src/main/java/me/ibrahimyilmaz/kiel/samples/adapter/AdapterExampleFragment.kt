package me.ibrahimyilmaz.kiel.samples.adapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.*
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Poll.PollOption
import me.ibrahimyilmaz.kiel.samples.adapter.viewholder.*
import me.ibrahimyilmaz.kiel.samples.utils.MarginItemDecoration

class AdapterExampleFragment : Fragment(R.layout.fragment_adapter_example) {

    private lateinit var messagesRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView)

        val recyclerViewAdapter = adapterOf<MessageViewState> {
            register(
                layoutResource = R.layout.adapter_message_text_item,
                viewHolder = ::TextMessageViewHolder,
                onBindViewHolder = { vh, _, text ->
                    vh.messageText.text = text.text
                    vh.sentAt.text = text.sentAt
                }
            )

            register(
                layoutResource = R.layout.adapter_message_image_item,
                viewHolder = ::ImageMessageViewHolder,
                onBindViewHolder = { vh, _, item ->
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
                onBindViewHolder = { vh, _, poll ->
                    vh.pollTitle.text = poll.text
                    vh.sentAt.text = poll.sentAt

                    val pollOptionsAdapter = adapterOf<PollOption> {
                        register(
                            layoutResource = R.layout.adapter_poll_option_item,
                            viewHolder = ::PollOptionViewHolder,
                            onBindViewHolder = { pollOptionViewHolder, _, pollOption ->
                                pollOptionViewHolder.pollOption.text = pollOption.text
                            }
                        )
                    }

                    vh.pollOptionsRecyclerView.adapter = pollOptionsAdapter

                    pollOptionsAdapter.submitList(poll.options)
                }
            )

            register(
                layoutResource = R.layout.adapter_list_image_message_item,
                viewHolder = ::ListImageMessageViewHolder,
                onBindViewHolder = { vh, _, item ->
                    vh.listTitle.text = item.text
                    vh.sentAt.text = item.sentAt

                    val adapter = adapterOf<Image> {
                        register(
                            layoutResource = R.layout.adapter_child_message_image_item,
                            viewHolder = ::ImageMessageViewHolder,
                            onBindViewHolder = { vh, _, item ->
                                vh.messageText.text = item.text
                                vh.sentAt.text = item.sentAt

                                Glide.with(vh.messageImage)
                                    .load(item.imageUrl)
                                    .into(vh.messageImage)
                            }
                        )
                    }

                    vh.listRecyclerView.adapter = adapter
                    adapter.submitList(item.images)
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

        val images = mutableListOf<Image>()
        for (i in 0..10) {
            images.add(
                Image(
                    "Looks amazing, isn't it?",
                    "https://upload.wikimedia.org/wikipedia/sco/thumb/7/7a/Manchester_United_FC_crest.svg/1200px-Manchester_United_FC_crest.svg.png",
                    "09:10 AM"
                )
            )
        }
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
            ),
            ListImage(
                "Where should I have my dinner?",
                images,
                "15:15 PM"
            )
        )
        recyclerViewAdapter.submitList(data)
    }
}
