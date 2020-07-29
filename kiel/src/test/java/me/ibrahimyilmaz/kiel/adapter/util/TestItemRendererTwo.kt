package me.ibrahimyilmaz.kiel.adapter.util

import android.view.View
import android.view.ViewGroup
import me.ibrahimyilmaz.kiel.item.LayoutResourceRenderer

class TestItemRendererTwo(
    override val layoutRes: Int
) : LayoutResourceRenderer<TestItemTwo> {

    override fun createView(parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun render(view: View, item: TestItemTwo) {
        TODO("Not yet implemented")
    }
}