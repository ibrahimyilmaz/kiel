package me.ibrahimyilmaz.kiel.samples.datasource

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.adapter.RecyclerAdapter
import me.ibrahimyilmaz.kiel.datasource.RecyclerDataSource
import me.ibrahimyilmaz.kiel.renderer.Renderer
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Text
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Image
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Poll
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Poll.PollOption
import me.ibrahimyilmaz.kiel.samples.datasource.viewbinder.ImageMessageViewBinder
import me.ibrahimyilmaz.kiel.samples.datasource.viewbinder.PollMessageViewBinder
import me.ibrahimyilmaz.kiel.samples.datasource.viewbinder.TextMessageViewBinder
import me.ibrahimyilmaz.kiel.samples.utils.MarginItemDecoration

class DataSourceExampleFragment : Fragment(R.layout.fragment_datasource_example) {

    private lateinit var messagesRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView)

        val dataSource = RecyclerDataSource(
            renderers = listOf(
                Renderer(
                    Text::class.java,
                    R.layout.adapter_message_text_item,
                    ::TextMessageViewBinder
                ),
                Renderer(
                    Image::class.java,
                    R.layout.adapter_message_image_item,
                    ::ImageMessageViewBinder
                ),
                Renderer(
                    Poll::class.java,
                    R.layout.adapter_message_poll_item,
                    ::PollMessageViewBinder
                )
            )
        )
        messagesRecyclerView.adapter = RecyclerAdapter(dataSource)
        messagesRecyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.padding_normal).toInt()
            )
        )
        dataSource.setData(
            listOf(
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
        )
    }

}