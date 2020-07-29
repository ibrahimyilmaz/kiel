package me.ibrahimyilmaz.kiel.adapter

interface RecyclerDiffCallbackFactory<T : Any> {
    fun create(oldItems: List<T>, newItems: List<T>): RecyclerDiffCallback<T>
}