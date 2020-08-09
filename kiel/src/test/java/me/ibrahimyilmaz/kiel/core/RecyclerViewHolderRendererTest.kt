package me.ibrahimyilmaz.kiel.core

import androidx.collection.SimpleArrayMap
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class RecyclerViewHolderRendererTest {

    private lateinit var itemTypes: SimpleArrayMap<Class<*>, Int>

    private lateinit var bindViewHolderListeners:
        SimpleArrayMap<Class<*>, OnBindViewHolder<Any, RecyclerViewHolder<Any>>>

    private lateinit var bindViewHolderWithPayloadListeners:
        SimpleArrayMap<Class<*>, OnBindViewHolderWithPayload<Any, RecyclerViewHolder<Any>>>

    private lateinit var renderer: RecyclerViewHolderRenderer<Any, RecyclerViewHolder<Any>>

    @Before
    fun setUp() {
        itemTypes = SimpleArrayMap()
        bindViewHolderListeners = SimpleArrayMap()
        bindViewHolderWithPayloadListeners = SimpleArrayMap()
    }

    @Test
    fun `Should return item view type for the given item`() {
        // GIVEN
        val expectedItemViewType = 1
        setUpItemTypes(Any(), expectedItemViewType)

        renderer =
            RecyclerViewHolderRenderer(
                itemTypes,
                bindViewHolderListeners,
                bindViewHolderWithPayloadListeners
            )
        // WHEN
        val actualItemViewType = renderer.getItemViewType(Any())

        // THEN
        assertThat(actualItemViewType).isEqualTo(expectedItemViewType)
    }

    @Test
    fun `Should render viewHolder with the given viewHolder and item`() {
        // GIVEN
        renderer =
            RecyclerViewHolderRenderer(
                itemTypes,
                bindViewHolderListeners,
                bindViewHolderWithPayloadListeners
            )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()

        // WHEN
        renderer.renderViewHolder(viewHolder, position, item)

        // THEN
        verify(viewHolder).bind(position, item)
    }

    @Test
    fun `Should notify the ViewHolderBound listener`() {
        // GIVEN
        val onBoundListener = mock<OnBindViewHolder<Any, RecyclerViewHolder<Any>>>()
        setUpViewHolderBoundListeners(Any(), onBoundListener)
        renderer =
            RecyclerViewHolderRenderer(
                itemTypes,
                bindViewHolderListeners,
                bindViewHolderWithPayloadListeners
            )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()

        // WHEN
        renderer.renderViewHolder(viewHolder, position, item)

        // THEN
        verify(onBoundListener).invoke(viewHolder, position, item)
    }

    @Test
    fun `Should render viewHolder with the given viewHolder, item and payload`() {
        // GIVEN
        renderer =
            RecyclerViewHolderRenderer(
                itemTypes,
                bindViewHolderListeners,
                bindViewHolderWithPayloadListeners
            )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()
        val payloads = mock<List<Any>>()

        // WHEN
        renderer.renderViewHolder(viewHolder, position, item, payloads)

        // THEN
        verify(viewHolder).bind(position, item, payloads)
    }

    @Test
    fun `Should notify onBindViewHolderWithPayload listener`() {
        // GIVEN
        val onBoundListener = mock<OnBindViewHolderWithPayload<Any, RecyclerViewHolder<Any>>>()
        setUpViewHolderBoundWithPayloadListeners(Any(), onBoundListener)
        renderer =
            RecyclerViewHolderRenderer(
                itemTypes,
                bindViewHolderListeners,
                bindViewHolderWithPayloadListeners
            )
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = Any()
        val payloads = mock<List<Any>>()

        // WHEN
        renderer.renderViewHolder(viewHolder, position, item, payloads)

        // THEN
        verify(onBoundListener).invoke(viewHolder, position, item, payloads)
    }

    private fun setUpViewHolderBoundListeners(
        item: Any,
        listener: OnBindViewHolder<Any, RecyclerViewHolder<Any>>
    ) {
        bindViewHolderListeners.put(item.javaClass, listener)
    }

    private fun setUpViewHolderBoundWithPayloadListeners(
        item: Any,
        listener: OnBindViewHolderWithPayload<Any, RecyclerViewHolder<Any>>
    ) {
        bindViewHolderWithPayloadListeners.put(item.javaClass, listener)
    }

    private fun setUpItemTypes(item: Any, itemViewType: Int) {
        itemTypes.put(item.javaClass, itemViewType)
    }
}