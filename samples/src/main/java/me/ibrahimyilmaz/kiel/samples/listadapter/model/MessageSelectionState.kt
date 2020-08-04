package me.ibrahimyilmaz.kiel.samples.listadapter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class MessageSelectionState : Parcelable {
    @Parcelize
    object Selected : MessageSelectionState()

    @Parcelize
    object Normal : MessageSelectionState()
}