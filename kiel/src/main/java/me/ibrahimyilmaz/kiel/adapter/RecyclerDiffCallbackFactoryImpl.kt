package me.ibrahimyilmaz.kiel.adapter

class RecyclerDiffCallbackFactoryImpl<T : Any>
    : RecyclerDiffCallbackFactory<T> {

    override fun create(
        oldItems: List<T>,
        newItems: List<T>
    ) = RecyclerDiffCallback(
        oldItems,
        newItems
    )
}