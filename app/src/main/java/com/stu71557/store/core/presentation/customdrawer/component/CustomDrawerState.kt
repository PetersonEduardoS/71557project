package com.stu71557.store.core.presentation.customdrawer.component

enum class CustomDrawerState {
    Opened,
    Closed
}

fun CustomDrawerState.isOpened(): Boolean = this.name == "Opened"

fun CustomDrawerState.opposite(): CustomDrawerState {
    return  if (this == CustomDrawerState.Opened) CustomDrawerState.Closed
    else CustomDrawerState.Opened
}