package me.ibrahimyilmaz.kiel.samples.listadapter

import android.os.Bundle
import android.view.ActionMode
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import me.ibrahimyilmaz.kiel.listadapter.RecyclerViewListAdapter.Companion.listAdapterOf
import me.ibrahimyilmaz.kiel.samples.R
import me.ibrahimyilmaz.kiel.samples.listadapter.model.MessageListItemViewState
import me.ibrahimyilmaz.kiel.samples.listadapter.viewholder.MessageDiffUtilItemCallback
import me.ibrahimyilmaz.kiel.samples.listadapter.viewholder.MessageViewHolder

class RecyclerViewListAdapterExampleFragment :
    Fragment(R.layout.fragment_recyclerviewlistadapter_example), MessageActionModeCallbackListener {

    private val viewModel by viewModels<RecyclerViewListAdapterExampleFragmentViewModel>()
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

    private val messageListAdapter = listAdapterOf<MessageListItemViewState> {
        itemDiffUtil(::MessageDiffUtilItemCallback)
        register(
            layoutResource = R.layout.adapter_message_list_item,
            viewHolder = ::MessageViewHolder,
            onViewHolderCreated = { vh ->
                vh.itemView.setOnLongClickListener {
                    val item = vh.boundItem ?: return@setOnLongClickListener false
                    viewModel.onItemLongClicked(item)
                    true
                }
            }
        )
    }

    override fun onDeleteActionClicked() = viewModel.onDeleteActionClicked()

    override fun onActionModeDestroyed() {
        actionMode = null
        viewModel.onActionDeleteModeFinished()
    }
}
