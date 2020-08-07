package me.ibrahimyilmaz.kiel.adapter

import com.google.common.truth.Truth.assertThat
import me.ibrahimyilmaz.kiel.utils.TestRecyclerViewHolder
import org.junit.Before
import org.junit.Test


class RecyclerViewAdapterBuilderTest {

    private lateinit var recyclerViewAdapterBuilder: RecyclerViewAdapterBuilder<Any>

    @Before
    fun setUp() {
        recyclerViewAdapterBuilder = RecyclerViewAdapterBuilder()
    }

    @Test
    fun `Should build an instance of RecyclerViewAdapter`() {
        // GIVEN
        recyclerViewAdapterBuilder.register {
            layoutResource { 1 }
            viewHolder { ::TestRecyclerViewHolder }
            type { Any::class.java }
        }
        // WHEN
        val adapter = recyclerViewAdapterBuilder.build()

        // THEN
        assertThat(adapter).isInstanceOf(RecyclerViewAdapter::class.java)
    }
}