package me.ibrahimyilmaz.kiel.adapter

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import me.ibrahimyilmaz.kiel.adapter.util.TestItem
import me.ibrahimyilmaz.kiel.adapter.util.TestItemRenderer
import me.ibrahimyilmaz.kiel.adapter.util.TestItemRendererTwo
import me.ibrahimyilmaz.kiel.adapter.util.TestItemTwo
import me.ibrahimyilmaz.kiel.item.LayoutResourceRenderer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecyclerPagerDataSourceTest {

    @Mock
    private lateinit var adapter: RecyclerPagerAdapter<Any>
    private val rendererOne: LayoutResourceRenderer<Any> = TestItemRenderer(1)
    private val rendererTwo: LayoutResourceRenderer<Any> = TestItemRendererTwo(2)
    private val itemOne = TestItem(1)

    private lateinit var dataSource: RecyclerPagerDataSource<Any>

    @Before
    fun setUp() {
        dataSource = RecyclerPagerDataSource(
            mapOf(
                TestItem::class.java to rendererOne,
                TestItemTwo::class.java to rendererTwo
            )
        )
        dataSource.attachToAdapter(adapter)
    }

    @Test
    fun `Should return correct renderer for the given type`() {
        // WHEN
        val actualRenderer = dataSource.getRendererOf(rendererOne.itemViewType)

        // THEN
        assertThat(actualRenderer).isEqualTo(rendererOne)
    }

    @Test
    fun `Should return correct layout resource for the given position`() {
        // WHEN
        val actualItemViewType = dataSource.getItemViewType(itemOne)

        // THEN
        assertThat(actualItemViewType).isEqualTo(rendererOne.itemViewType)
    }

    @Test
    fun `Should invoke submit data function of the adapter when the submit data is called`() =
        runBlocking {
            // GIVEN
            val pagingData = mock<PagingData<Any>>()

            // WHEN
            dataSource.submitData(pagingData)

            // THEN
            verify(adapter).submitData(pagingData)
        }

    @Test
    fun `Should invoke submit data function of the adapter when the submit data with lifecycle is called`() {
        // GIVEN
        val pagingData = mock<PagingData<Any>>()
        val lifecycle = mock<Lifecycle>()

        // WHEN
        dataSource.submitData(lifecycle, pagingData)

        // THEN
        verify(adapter).submitData(lifecycle, pagingData)
    }

    @Test
    fun `Should invoke retry function of the adapter when the retry method is called`() {
        // WHEN
        dataSource.retry()

        // THEN
        verify(adapter).retry()
    }

    @Test
    fun `Should invoke refresh function of the adapter when the refresh method is called`() {
        // WHEN
        dataSource.refresh()

        // THEN
        verify(adapter).refresh()
    }
}