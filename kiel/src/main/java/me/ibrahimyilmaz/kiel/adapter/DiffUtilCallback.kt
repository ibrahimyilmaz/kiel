package me.ibrahimyilmaz.kiel.adapter

typealias DiffUtilCallback<T> = (oldItems: List<T>, newItems: List<T>) -> RecyclerDiffUtilCallback<T>
