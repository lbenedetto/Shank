package com.lbenedetto.shank

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Constructor

abstract class ConstructableActivity<ViewClass, PresenterClass>(
    private var constructor: Constructor<PresenterClass>,
    private var mContext: Context,
    vararg any: Any
) : AppCompatActivity(), Constructable<ViewClass> {
    private var id: Int = 0
    var mView: ViewClass? = null

    private val mIntent = Intent()

    init {
        mIntent.putExtra("UniversalBundle", UniversalBundle.makeBundle(any))
    }

    fun start() {
        mContext.startActivity(mIntent)
    }

    fun startForResult(requestCode: Int) {
        if (mContext is Activity) (mContext as Activity).startActivityForResult(mIntent, requestCode)
        else throw IllegalStateException("Shank: Cannot startActivityForResult from Context that is not an Activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            initFromBundle(savedInstanceState)
        } else {
            initFromBundle(intent.getBundleExtra("UniversalBundle"))
        }

        id = intent!!.extras!!["AppCompatActivityPresenter_ID"] as Int
    }

    private fun initFromBundle(bundle: Bundle): PresenterClass {
        return constructor.newInstance(*UniversalBundle.fromBundle(bundle))
    }

    fun actuallySaveInstanceState(vararg any: Any) {
        onSaveInstanceState(UniversalBundle.makeBundle(any))
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