package me.ibrahimyilmaz.kiel.adapter

internal class RecyclerDiffUtilCallbackFactoryImpl<T : Any>
    : RecyclerDiffUtilCallbackFactory<T> {

    override fun create(
        oldItems: List<T>,
        newItems: List<T>
    ) = RecyclerDiffUtilCallback(
        oldItems,
        newItems
    )
}