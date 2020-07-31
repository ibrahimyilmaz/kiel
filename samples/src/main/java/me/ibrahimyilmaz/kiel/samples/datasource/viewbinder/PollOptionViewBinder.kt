package me.ibrahimyilmaz.kiel.samples.datasource.viewbinder

import android.view.View
import android.widget.RadioButton
import me.ibrahimyilmaz.kiel.binder.ViewBinder
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.datasource.model.MessageViewState.Poll.PollOption

class PollOptionViewBinder(view: View) : ViewBinder<PollOption>(view) {
    private val pollOption = view.findViewById<RadioButton>(R.id.pollOption)
    override fun bind(item: PollOption) {
        pollOption.text = item.text
    }
}