package me.ibrahimyilmaz.kiel.samples.datasource.viewbinder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.adapter.RecyclerAdapter
import me.ibrahimyilmaz.kiel.binder.ViewBinder
import me.ibrahimyilmaz.kiel.datasource.RecyclerDataSource
import me.ibrahimyilmaz.kiel.renderer.Renderer
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Poll
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Poll.PollOption

class PollMessageViewBinder(view: View) : ViewBinder<Poll>(view) {
    private val pollTitle = view.findViewById<TextView>(R.id.pollTitle)
    private val sentAt = view.findViewById<TextView>(R.id.sentAt)
    private val pollOptionsRecyclerView =
        view.findViewById<RecyclerView>(R.id.pollOptionsRecyclerView)

    override fun bind(item: Poll) {
        pollTitle.text = item.text
        sentAt.text = item.sentAt
        val dataSource = RecyclerDataSource(
            listOf(
                Renderer(
                    PollOption::class.java,
                    R.layout.adapter_poll_option_item,
                    ::PollOptionViewBinder
                )
            )
        )

        pollOptionsRecyclerView.adapter = RecyclerAdapter(dataSource)

        dataSource.setData(item.options)
    }

}