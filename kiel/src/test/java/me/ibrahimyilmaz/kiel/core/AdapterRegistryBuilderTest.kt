package me.ibrahimyilmaz.kiel.core

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import me.ibrahimyilmaz.kiel.utils.TestRecyclerViewHolder
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdapterRegistryBuilderTest {

    @Test(expected = IllegalArgumentException::class)
    fun `Should throw an Illegal Argument Exception when view type is not provided`() {
        // GIVEN
        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                layoutResource { 1 }
                viewHolder { ::TestRecyclerViewHolder }
            }

        // WHEN
        recyclerViewAdapterRegistryBuilder.build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Should throw an Illegal Argument Exception when layout resource is not provided`() {
        // GIVEN
        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                viewHolder { ::TestRecyclerViewHolder }
            }

        // WHEN
        recyclerViewAdapterRegistryBuilder.build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Should throw an Illegal Argument Exception when viewHolder constructor is not provided`() {
        // GIVEN
        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
            }

        // WHEN
        recyclerViewAdapterRegistryBuilder.build()
    }

    @Test
    fun `Should return a RecyclerViewAdapterRegistry instance when type, layoutResource and viewHolder constructor are provided`() {
        // GIVEN
        val expectedRecyclerViewAdapterRegistry =
            RecyclerViewAdapterRegistry<Any, RecyclerViewHolder<Any>>(
                Any::class.java,
                ::TestRecyclerViewHolder,
                1
            )

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder { ::TestRecyclerViewHolder }
            }

        // WHEN
        val actualRecyclerViewAdapterRegistry = recyclerViewAdapterRegistryBuilder.build()

        // THEN
        assertRecyclerViewAdapterRegistry(
            actualRecyclerViewAdapterRegistry,
            expectedRecyclerViewAdapterRegistry
        )
    }

    @Test
    fun `Should return a RecyclerViewAdapterRegistry with OnViewHolderCreated listener when it is provided`() {
        // GIVEN
        val onViewHolderCreatedListener = mock<OnViewHolderCreated<TestRecyclerViewHolder>>()

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder { ::TestRecyclerViewHolder }
                onViewHolderCreated(onViewHolderCreatedListener)
            }

        // WHEN
        val actualRecyclerViewAdapterRegistry = recyclerViewAdapterRegistryBuilder.build()

        // THEN
        assertThat(actualRecyclerViewAdapterRegistry.onViewHolderCreated).isNotNull()
    }

    @Test
    fun `Should return a RecyclerViewAdapterRegistry with OnViewHolderBound listener when it is provided`() {
        // GIVEN
        val onViewHolderBoundListener = mock<OnViewHolderBound<Any, TestRecyclerViewHolder>>()

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder { ::TestRecyclerViewHolder }
                onViewHolderBound(onViewHolderBoundListener)
            }

        // WHEN
        val actualRecyclerViewAdapterRegistry = recyclerViewAdapterRegistryBuilder.build()

        // THEN
        assertThat(actualRecyclerViewAdapterRegistry.onViewHolderBound).isNotNull()
    }

    @Test
    fun `Should return a RecyclerViewAdapterRegistry with OnViewHolderBoundWithPayload listener when it is provided`() {
        // GIVEN
        val onViewHolderBoundWithPayloadListener =
            mock<OnViewHolderBoundWithPayload<Any, TestRecyclerViewHolder>>()

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder { ::TestRecyclerViewHolder }
                onViewHolderBoundWithPayload(onViewHolderBoundWithPayloadListener)
            }

        // WHEN
        val actualRecyclerViewAdapterRegistry = recyclerViewAdapterRegistryBuilder.build()

        // THEN
        assertThat(actualRecyclerViewAdapterRegistry.onViewHolderBoundWithPayload).isNotNull()
    }

    private fun <T : Any, VH : RecyclerViewHolder<T>> assertRecyclerViewAdapterRegistry(
        actualRecyclerViewAdapterRegistry: RecyclerViewAdapterRegistry<T, VH>,
        expectedRecyclerViewAdapterRegistry: RecyclerViewAdapterRegistry<T, VH>
    ) {
        assertThat(actualRecyclerViewAdapterRegistry.type).isEqualTo(
            expectedRecyclerViewAdapterRegistry.type
        )
        assertThat(actualRecyclerViewAdapterRegistry.layoutResource).isEqualTo(
            expectedRecyclerViewAdapterRegistry.layoutResource
        )
        assertThat(actualRecyclerViewAdapterRegistry.viewHolderIntrospection).isEqualTo(
            expectedRecyclerViewAdapterRegistry.viewHolderIntrospection
        )
    }
}