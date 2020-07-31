package me.ibrahimyilmaz.kiel.datasource

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import me.ibrahimyilmaz.kiel.adapter.RecyclerPagerAdapter
import me.ibrahimyilmaz.kiel.renderer.Renderer
import me.ibrahimyilmaz.kiel.datasource.util.TestItem
import me.ibrahimyilmaz.kiel.datasource.util.TestItemTwo
import me.ibrahimyilmaz.kiel.datasource.util.TestItemTwoViewBinder
import me.ibrahimyilmaz.kiel.datasource.util.TestItemViewBinder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecyclerPagerDataSourceTest {

    @Mock
    private lateinit var adapter: RecyclerPagerAdapter<Any>
    private val rendererOne: Renderer<Any> =
        Renderer(TestItem::class.java, 1, ::TestItemViewBinder)
    private val rendererTwo: Renderer<Any> =
        Renderer(TestItemTwo::class.java, 2, ::TestItemTwoViewBinder)

    private val itemOne = TestItem(1)

    private lateinit var dataSource: RecyclerPagerDataSource<Any>

    @Before
    fun setUp() {
        dataSource = RecyclerPagerDataSource(
            listOf(
                rendererOne,
                rendererTwo
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