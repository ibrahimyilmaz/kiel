package me.ibrahimyilmaz.kiel.adapter

import androidx.collection.SimpleArrayMap
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class RecyclerViewHolderRendererTest {

    private lateinit var itemTypes: SimpleArrayMap<Class<*>, Int>

    private lateinit var viewHolderBoundListeners: SimpleArrayMap<Class<*>, OnViewHolderBound<Any, RecyclerViewHolder<Any>>>

    private lateinit var viewHolderBoundWithPayloadListeners: SimpleArrayMap<Class<*>, OnViewHolderBoundWithPayload<Any, RecyclerViewHolder<Any>>>

    private lateinit var recyclerViewHolderRenderer: RecyclerViewHolderRenderer<Any, RecyclerViewHolder<Any>>

    @Before
    fun setUp() {
        itemTypes = SimpleArrayMap()
        viewHolderBoundListeners = SimpleArrayMap()
        viewHolderBoundWithPayloadListeners = SimpleArrayMap()
    }

    @Test
    fun `Should return item view type for the given item`() {
        // GIVEN
        val expectedItemViewType = 1
        setUpItemTypes(Any(), expectedItemViewType)

        recyclerViewHolderRenderer = RecyclerViewHolderRenderer(
            itemTypes,
            viewHolderBoundListeners,
            viewHolderBoundWithPayloadListeners
        )
        // WHEN
        val actualItemViewType = recyclerViewHolderRenderer.getItemViewType(Any())

        // THEN
        assertThat(actualItemViewType).isEqualTo(expectedItemViewType)
    }

    @Test
    fun `Should render viewHolder with the given viewHolder and item`() {
        // GIVEN
        recyclerViewHolderRenderer = RecyclerViewHolderRenderer(
            itemTypes,
            viewHolderBoundListeners,
            viewHolderBoundWithPayloadListeners
        )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()

        // WHEN
        recyclerViewHolderRenderer.renderViewHolder(viewHolder, position, item)

        // THEN
        verify(viewHolder).bind(position, item)
    }

    @Test
    fun `Should notify the ViewHolderBound listener when the viewHolder is rendered with item and position`() {
        // GIVEN
        val onBoundListener = mock<OnViewHolderBound<Any, RecyclerViewHolder<Any>>>()
        setUpViewHolderBoundListeners(Any(), onBoundListener)
        recyclerViewHolderRenderer = RecyclerViewHolderRenderer(
            itemTypes,
            viewHolderBoundListeners,
            viewHolderBoundWithPayloadListeners
        )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()

        // WHEN
        recyclerViewHolderRenderer.renderViewHolder(viewHolder, position, item)

        // THEN
        verify(onBoundListener).invoke(viewHolder, position, item)
    }

    @Test
    fun `Should render viewHolder with the given viewHolder, item and payload`() {
        // GIVEN
        recyclerViewHolderRenderer = RecyclerViewHolderRenderer(
            itemTypes,
            viewHolderBoundListeners,
            viewHolderBoundWithPayloadListeners
        )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()
        val payloads = mock<List<Any>>()

        // WHEN
        recyclerViewHolderRenderer.renderViewHolder(viewHolder, position, item, payloads)

        // THEN
        verify(viewHolder).bind(position, item, payloads)
    }

    @Test
    fun `Should notify the ViewHolderBoundWithPayload listener when the viewHolder is rendered with item, position and payload `() {
        // GIVEN
        val onBoundListener = mock<OnViewHolderBoundWithPayload<Any, RecyclerViewHolder<Any>>>()
        setUpViewHolderBoundWithPayloadListeners(Any(), onBoundListener)
        recyclerViewHolderRenderer = RecyclerViewHolderRenderer(
            itemTypes,
            viewHolderBoundListeners,
            viewHolderBoundWithPayloadListeners
        )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()
        val payloads = mock<List<Any>>()

        // WHEN
        recyclerViewHolderRenderer.renderViewHolder(viewHolder, position, item, payloads)

        // THEN
        verify(onBoundListener).invoke(viewHolder, position, item, payloads)
    }

    private fun setUpViewHolderBoundListeners(
        item: Any,
        listener: OnViewHolderBound<Any, RecyclerViewHolder<Any>>
    ) {
        viewHolderBoundListeners.put(item.javaClass, listener)
    }

    private fun setUpViewHolderBoundWithPayloadListeners(
        item: Any,
        listener: OnViewHolderBoundWithPayload<Any, RecyclerViewHolder<Any>>
    ) {
        viewHolderBoundWithPayloadListeners.put(item.javaClass, listener)
    }

    private fun setUpItemTypes(item: Any, itemViewType: Int) {
        itemTypes.put(item.javaClass, itemViewType)
    }
}