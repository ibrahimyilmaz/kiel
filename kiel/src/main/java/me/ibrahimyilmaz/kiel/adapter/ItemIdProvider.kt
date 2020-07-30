package me.ibrahimyilmaz.kiel.adapter

typealias ItemIdProviderOperator<T> = (item: T) -> Long

interface ItemIdProvider<T> {
    fun provideId(item: T): Long
}