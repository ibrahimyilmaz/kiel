package me.ibrahimyilmaz.kiel.adapter.util

import android.view.View
import android.view.ViewGroup
import me.ibrahimyilmaz.kiel.item.LayoutResourceRenderer

class TestItemRenderer(
    override val layoutRes: Int
) : LayoutResourceRenderer<TestItem> {

    override fun createView(parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun render(view: View, item: TestItem) {
        TODO("Not yet implemented")
    }
}