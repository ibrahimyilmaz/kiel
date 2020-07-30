package me.ibrahimyilmaz.kiel.datasource

import com.google.common.truth.Truth.assertThat
import me.ibrahimyilmaz.kiel.datasource.util.TestItem
import me.ibrahimyilmaz.kiel.datasource.util.TestItemViewHolderBinder
import me.ibrahimyilmaz.kiel.datasource.util.TestItemViewHolderBinderTwo
import me.ibrahimyilmaz.kiel.datasource.util.TestItemTwo
import me.ibrahimyilmaz.kiel.binder.LayoutResourceViewHolderBinder
import org.junit.Before
import org.junit.Test

class RecyclerDataSourceTest {

    private val rendererOne: LayoutResourceViewHolderBinder<Any> =
        TestItemViewHolderBinder(1)
    private val rendererTwo: LayoutResourceViewHolderBinder<Any> =
        TestItemViewHolderBinderTwo(2)
    private val itemOne = TestItem(1)
    private val itemTwo =
        TestItemTwo("ibra")
    private val itemThree = TestItem(3)

    private lateinit var dataSource: RecyclerDataSource<Any>

    @Before
    fun setUp() {
        dataSource = RecyclerDataSource(
            mapOf(
                TestItem::class.java to rendererOne,
                TestItemTwo::class.java to rendererTwo
            )
        )

        dataSource.seedData(listOf(itemOne, itemTwo, itemThree))
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
        val actualItemViewType = dataSource.getItemViewType(0)

        // THEN
        assertThat(actualItemViewType).isEqualTo(rendererOne.itemViewType)
    }

    @Test
    fun `Should return correct item for the given position`() {
        // THEN
        assertThat(dataSource[0]).isEqualTo(itemOne)
        assertThat(dataSource[1]).isEqualTo(itemTwo)
        assertThat(dataSource[2]).isEqualTo(itemThree)
    }
}