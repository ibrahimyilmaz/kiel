package me.ibrahimyilmaz.kiel.adapter

import com.google.common.truth.Truth.assertThat
import me.ibrahimyilmaz.kiel.core.AdapterBuilder
import me.ibrahimyilmaz.kiel.pageradapter.RecyclerViewPagingDataAdapter
import me.ibrahimyilmaz.kiel.pageradapter.RecyclerViewPagingDataAdapterFactory
import me.ibrahimyilmaz.kiel.utils.TestRecyclerViewHolder
import org.junit.Before
import org.junit.Test

class RecyclerViewAdapterBuilderTest {

    private lateinit var adapterBuilder: AdapterBuilder<Any>

    @Before
    fun setUp() {
        adapterBuilder = AdapterBuilder()
    }

    @Test
    fun `Should build an instance of RecyclerViewAdapter`() {
        // GIVEN
        adapterBuilder.register(
            viewHolder = ::TestRecyclerViewHolder,
            layoutResource = 1
        )

        // WHEN
        val adapter = adapterBuilder.build(RecyclerViewAdapterFactory())

        // THEN
        assertThat(adapter).isInstanceOf(RecyclerViewAdapter::class.java)
    }

    @Test
    fun `Should build an instance of RecyclerViewPagingDataAdapter`() {
        // GIVEN
        adapterBuilder.register(
            viewHolder = ::TestRecyclerViewHolder,
            layoutResource = 1
        )

        // WHEN
        val adapter = adapterBuilder.build(RecyclerViewPagingDataAdapterFactory())

        // THEN
        assertThat(adapter).isInstanceOf(RecyclerViewPagingDataAdapter::class.java)
    }
}