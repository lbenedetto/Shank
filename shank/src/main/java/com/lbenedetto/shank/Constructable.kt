package com.lbenedetto.shank

interface Constructable<ViewClass> {
    fun setView(view: ViewClass)
    fun getView(): ViewClass?
}