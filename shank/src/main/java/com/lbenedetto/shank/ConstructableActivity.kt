package com.lbenedetto.shank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class ConstructableActivity<ViewClass> : AppCompatActivity(), Constructable<ViewClass> {
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent!!.extras!!["AppCompatActivityPresenter_ID"] as Int
    }

    override fun readyForPresenter(){
        ShankPresenter.bind(id, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ShankPresenter.unregister(id)
    }
}