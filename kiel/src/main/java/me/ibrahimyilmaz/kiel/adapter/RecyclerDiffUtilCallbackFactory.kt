package me.ibrahimyilmaz.kiel.adapter

interface RecyclerDiffUtilCallbackFactory<T : Any> {
    fun create(oldItems: List<T>, newItems: List<T>): RecyclerDiffUtilCallback<T>
}