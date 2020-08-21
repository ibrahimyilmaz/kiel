package me.ibrahimyilmaz.kiel.samples.listadapter

import android.os.Bundle
import android.view.ActionMode
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageSelectionState
import me.ibrahimyilmaz.kiel.samples.listadapter.viewholder.MessageViewHolder

class DiffExampleFragment :
    Fragment(R.layout.fragment_diff_example), MessageActionModeCallbackListener {

    private val viewModel by viewModels<DiffExampleFragmentViewModel>()
    private lateinit var messageListRecyclerView: RecyclerView
    private var actionMode: ActionMode? = null

    private val actionModelCallback =
        MessageListItemSelectionActionModeCallback(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageListRecyclerView = view.findViewById(R.id.messageListRecyclerView)
        messageListRecyclerView.adapter = messageListAdapter
        messageListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.messages.observe(viewLifecycleOwner, Observer(::onMessagesChanged))
        viewModel.showDeleteAction.observe(
            viewLifecycleOwner,
            Observer(::onShowDeleteAction)
        )

        viewModel.showSelectedMessageCount.observe(
            viewLifecycleOwner,
            Observer(::showSelectedMessageCount)
        )
    }

    private fun showSelectedMessageCount(selectedMessageCount: Int) {
        actionMode?.title = "$selectedMessageCount"
    }

    private fun onMessagesChanged(messages: List<MessageListItemViewState>) {
        messageListAdapter.submitList(messages)
    }

    private fun onShowDeleteAction(shouldShow: Boolean) {
        if (shouldShow) {
            if (actionMode == null)
                actionMode = requireActivity().startActionMode(actionModelCallback)
        } else {
            actionMode?.finish()
        }
    }

    private val messageListAdapter = adapterOf<MessageListItemViewState> {
        diff(
            areContentsTheSame = { old, new -> old == new },
            areItemsTheSame = { old, new -> old.message.id == new.message.id },
            getChangePayload = { oldItem, newItem ->
                val diffBundle = Bundle()

                if (oldItem.selectionState != newItem.selectionState) {
                    diffBundle.putParcelable(
                        KEY_SELECTION,
                        newItem.selectionState
                    )
                }

                if (diffBundle.isEmpty) null else diffBundle
            }
        )
        register(
            layoutResource = R.layout.adapter_message_list_item,
            viewHolder = ::MessageViewHolder,
            onViewHolderCreated = { vh ->
                vh.itemView.setOnLongClickListener {
                    val item = vh.boundItem ?: return@setOnLongClickListener false
                    viewModel.onItemLongClicked(item)
                    true
                }
            },
            onBindViewHolder = { messageViewHolder, _, messageListItemViewState ->
                messageViewHolder.binding.senderText.text = messageListItemViewState.message.sender
                messageViewHolder.binding.contentText.text =
                    messageListItemViewState.message.content
                messageViewHolder.binding.imageLetter.text =
                    messageListItemViewState.message.sender.subSequence(0, 1)

                messageViewHolder.setSelectionState(messageListItemViewState.selectionState)
            },
            onBindViewHolderWithPayload = { messageViewHolder, _, _, payloads ->
                (payloads.first() as? Bundle)?.getParcelable<MessageSelectionState>(
                    KEY_SELECTION
                )?.let(messageViewHolder::setSelectionState)
            }
        )
    }

    override fun onDeleteActionClicked() = viewModel.onDeleteActionClicked()

    override fun onActionModeDestroyed() {
        actionMode = null
        viewModel.onActionDeleteModeFinished()
    }

    private companion object {
        const val KEY_SELECTION = "KEY_SELECTION"
    }
}
