package com.lbenedetto.shank

interface Constructable<ViewClass> {
    fun readyForPresenter()
    fun getView() : ViewClass

}