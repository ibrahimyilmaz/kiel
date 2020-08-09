package me.ibrahimyilmaz.kiel.samples.adapter.viewholder

import android.view.View
import android.widget.RadioButton
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.adapter.model.MessageViewState.Poll.PollOption

class PollOptionViewHolder(view: View) : RecyclerViewHolder<PollOption>(view) {
    val pollOption = view.findViewById<RadioButton>(R.id.pollOption)
}
