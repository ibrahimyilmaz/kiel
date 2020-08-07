package me.ibrahimyilmaz.kiel.samples.listadapter

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import me.ibrahimyilmaz.kiel.samples.R

class MessageListItemSelectionActionModeCallback(
    private val listener: MessageActionModeCallbackListener
) : ActionMode.Callback {
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) =
        when (item?.itemId) {
            R.id.deleteItem -> {
                listener.onDeleteActionClicked()
                mode?.finish()
                true
            }
            else -> false
        }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        val actionMode = mode ?: return false
        val actionMenu = menu ?: return false
        actionMode.menuInflater.inflate(R.menu.menu_message_list_item_selection, actionMenu)
        return true
    }

    override fun onPrepareActionMode(
        mode: ActionMode?,
        menu: Menu?
    ) = false

    override fun onDestroyActionMode(
        mode: ActionMode?
    ) = listener.onActionModeDestroyed()
}