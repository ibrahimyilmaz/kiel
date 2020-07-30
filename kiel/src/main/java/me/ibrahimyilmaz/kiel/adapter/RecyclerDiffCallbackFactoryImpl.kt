package me.ibrahimyilmaz.kiel.adapter

internal class RecyclerDiffCallbackFactoryImpl<T : Any>
    : RecyclerDiffCallbackFactory<T> {

    override fun create(
        oldItems: List<T>,
        newItems: List<T>
    ) = RecyclerDiffCallback(
        oldItems,
        newItems
    )
}