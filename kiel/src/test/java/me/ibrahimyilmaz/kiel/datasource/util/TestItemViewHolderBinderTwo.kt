package me.ibrahimyilmaz.kiel.datasource.util

import android.view.View
import android.view.ViewGroup
import me.ibrahimyilmaz.kiel.binder.LayoutResourceViewHolderBinder
import me.ibrahimyilmaz.kiel.datasource.util.TestItemTwo

class TestItemViewHolderBinderTwo(
    override val layoutRes: Int
) : LayoutResourceViewHolderBinder<TestItemTwo> {

    override fun createView(parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun render(view: View, item: TestItemTwo) {
        TODO("Not yet implemented")
    }
}