package me.ibrahimyilmaz.kiel.adapter

import com.google.common.truth.Truth.assertThat
import me.ibrahimyilmaz.kiel.utils.TestRecyclerViewHolder
import org.junit.Before
import org.junit.Test

class RecyclerViewAdapterBuilderTest {

    private lateinit var recyclerViewAdapterBuilder: RecyclerViewAdapterFactory<Any>

    @Before
    fun setUp() {
        recyclerViewAdapterBuilder = RecyclerViewAdapterFactory()
    }

    @Test
    fun `Should build an instance of RecyclerViewAdapter`() {
        // GIVEN
        recyclerViewAdapterBuilder.register(
            viewHolder = ::TestRecyclerViewHolder,
            layoutResource = 1
        )

        // WHEN
        val adapter = recyclerViewAdapterBuilder.create()

        // THEN
        assertThat(adapter).isInstanceOf(RecyclerViewAdapter::class.java)
    }
}