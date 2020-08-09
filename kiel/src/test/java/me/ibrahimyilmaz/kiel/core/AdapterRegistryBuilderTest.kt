package me.ibrahimyilmaz.kiel.core

import android.view.View
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import me.ibrahimyilmaz.kiel.utils.TestRecyclerViewHolder
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdapterRegistryBuilderTest {

    @Test(expected = IllegalArgumentException::class)
    fun `Should throw Illegal Argument Exception when view type is missing`() {
        // GIVEN
        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                layoutResource { 1 }
                viewHolder(::TestRecyclerViewHolder)
            }

        // WHEN
        recyclerViewAdapterRegistryBuilder.build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Should throw Illegal Argument Exception when layout resource is missing`() {
        // GIVEN
        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                viewHolder(::TestRecyclerViewHolder)
            }

        // WHEN
        recyclerViewAdapterRegistryBuilder.build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Should throw Illegal Argument Exception when viewHolder constructor is missing`() {
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
    fun `Should build RecyclerViewAdapterRegistry`() {
        // GIVEN
        val expectedRecyclerViewAdapterRegistry =
            RecyclerViewAdapterRegistry(
                Any::class.java,
                object : ViewHolderFactory<Any, RecyclerViewHolder<Any>> {
                    override fun instantiate(view: View): RecyclerViewHolder<Any> =
                        TestRecyclerViewHolder(view)
                },
                1
            )

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder(::TestRecyclerViewHolder)
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
    fun `Should build RecyclerViewAdapterRegistry with OnViewHolderCreated`() {
        // GIVEN
        val onViewHolderCreatedListener = mock<OnViewHolderCreated<TestRecyclerViewHolder>>()

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder(::TestRecyclerViewHolder)
                onViewHolderCreated(onViewHolderCreatedListener)
            }

        // WHEN
        val actualRecyclerViewAdapterRegistry = recyclerViewAdapterRegistryBuilder.build()

        // THEN
        assertThat(actualRecyclerViewAdapterRegistry.onViewHolderCreated).isNotNull()
    }

    @Test
    fun `Should build RecyclerViewAdapterRegistry with OnViewHolderBound`() {
        // GIVEN
        val onViewHolderBoundListener = mock<OnBindViewHolder<Any, TestRecyclerViewHolder>>()

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder(::TestRecyclerViewHolder)
                onViewHolderBound(onViewHolderBoundListener)
            }

        // WHEN
        val actualRecyclerViewAdapterRegistry = recyclerViewAdapterRegistryBuilder.build()

        // THEN
        assertThat(actualRecyclerViewAdapterRegistry.onBindViewHolder).isNotNull()
    }

    @Test
    fun `Should build RecyclerViewAdapterRegistry with OnViewHolderBoundWithPayload`() {
        // GIVEN
        val onViewHolderBoundWithPayloadListener =
            mock<OnBindViewHolderWithPayload<Any, TestRecyclerViewHolder>>()

        val recyclerViewAdapterRegistryBuilder =
            AdapterRegistryBuilder<Any>().apply {
                type { Any::class.java }
                layoutResource { 1 }
                viewHolder(::TestRecyclerViewHolder)
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
    }
}