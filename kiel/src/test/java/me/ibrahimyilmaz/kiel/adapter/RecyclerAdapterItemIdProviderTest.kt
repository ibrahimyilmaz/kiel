package me.ibrahimyilmaz.kiel.adapter

import android.os.Build
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import me.ibrahimyilmaz.kiel.datasource.RecyclerDataSource
import me.ibrahimyilmaz.kiel.datasource.util.TestItem
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class RecyclerAdapterItemIdProviderTest {

    @Mock
    private lateinit var dataSource: RecyclerDataSource<TestItem>

    private lateinit var recyclerAdapter: RecyclerAdapter<TestItem>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Should have stable id when the item id provider is provided`() {
        // WHEN
        recyclerAdapter = RecyclerAdapter(dataSource, mock())

        // THEN
        assertThat(recyclerAdapter.hasStableIds()).isTrue()
    }

    @Test
    fun `Should not have stable id when the item id provider is not provided`() {
        // WHEN
        recyclerAdapter = RecyclerAdapter(dataSource)

        // THEN
        assertThat(recyclerAdapter.hasStableIds()).isFalse()
    }
}