package me.ibrahimyilmaz.kiel.datasource.util

import android.view.View
import android.view.ViewGroup
import me.ibrahimyilmaz.kiel.binder.LayoutResourceViewHolderBinder
import me.ibrahimyilmaz.kiel.datasource.util.TestItem

class TestItemViewHolderBinder(
    override val layoutRes: Int
) : LayoutResourceViewHolderBinder<TestItem> {

    override fun createView(parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun render(view: View, item: TestItem) {
        TODO("Not yet implemented")
    }
}