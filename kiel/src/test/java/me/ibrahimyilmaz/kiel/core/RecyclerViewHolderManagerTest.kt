package me.ibrahimyilmaz.kiel.core

import android.view.ViewGroup
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecyclerViewHolderManagerTest {

    @Mock
    private lateinit var viewHolderFactory: RecyclerViewHolderFactory<Any, RecyclerViewHolder<Any>>

    @Mock
    private lateinit var renderer: RecyclerViewHolderRenderer<Any, RecyclerViewHolder<Any>>

    @InjectMocks
    private lateinit var viewHolderManager: RecyclerViewHolderManager<Any, RecyclerViewHolder<Any>>

    @Test
    fun `Should instantiate ViewHolder with provided viewType and parent`() {
        // GIVEN
        val parent = mock<ViewGroup>()
        val viewType = 1

        // WHEN
        viewHolderManager.instantiate(parent, viewType)

        // THEN
        verify(viewHolderFactory).instantiate(parent, viewType)
    }

    @Test
    fun `Should bind ViewHolder with the given viewHolder and item`() {
        // GIVEN
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = mock<Any>()

        // WHEN
        viewHolderManager.onBindViewHolder(viewHolder, position, item)

        // THEN
        verify(renderer).renderViewHolder(viewHolder, position, item)
    }

    @Test
    fun `Should bind ViewHolder with the given viewHolder, item and payloads`() {
        // GIVEN
        val viewHolder = mock<RecyclerViewHolder<Any>>()
        val position = 1
        val item = mock<Any>()
        val payloads = mock<List<Any>>()

        // WHEN
        viewHolderManager.onBindViewHolder(viewHolder, position, item, payloads)

        // THEN
        verify(renderer).renderViewHolder(viewHolder, position, item, payloads)
    }

    @Test
    fun `Should return viewType for the given item`() {
        // GIVEN
        val item = mock<Any>()

        // WHEN
        viewHolderManager.getItemViewType(item)

        // THEN
        verify(renderer).getItemViewType(item)
    }
}