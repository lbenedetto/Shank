package com.lbenedetto.shank

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class ConstructableActivity<ViewClass> : AppCompatActivity(), Constructable<ViewClass> {
    private var id: Int = 0
    var mView: ViewClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent!!.extras!!["AppCompatActivityPresenter_ID"] as Int
    }

    override fun setView(view: ViewClass) {
        mView = view
        setContentView(view as View)
        ShankPresenter.bind(id, this)
    }

    override fun getView(): ViewClass? {
        return mView
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing)
            ShankPresenter.unregister(id)
    }
}