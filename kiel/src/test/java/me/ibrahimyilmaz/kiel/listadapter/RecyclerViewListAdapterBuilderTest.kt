package me.ibrahimyilmaz.kiel.listadapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import me.ibrahimyilmaz.kiel.utils.TestRecyclerViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecyclerViewListAdapterBuilderTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var recyclerViewListAdapterBuilder: RecyclerViewListAdapterBuilder<Any>

    @Before
    fun setUp() {
        recyclerViewListAdapterBuilder = RecyclerViewListAdapterBuilder()
    }

    @Test
    fun `Should build an instance of RecyclerViewListAdapter`() {
        // GIVEN
        recyclerViewListAdapterBuilder.register {
            layoutResource { 1 }
            viewHolder { ::TestRecyclerViewHolder }
            type { Any::class.java }
        }
        // WHEN
        val adapter = recyclerViewListAdapterBuilder.build()

        // THEN
        assertThat(adapter).isInstanceOf(RecyclerViewListAdapter::class.java)
    }
}