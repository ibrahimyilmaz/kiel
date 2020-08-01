package me.ibrahimyilmaz.kiel.samples.datasource.viewbinder

import android.view.View
import android.widget.RadioButton
import me.ibrahimyilmaz.kiel.adapter.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Poll.PollOption


class PollOptionViewHolder(view: View) : RecyclerViewHolder<PollOption>(view) {
    val pollOption = view.findViewById<RadioButton>(R.id.pollOption)
}