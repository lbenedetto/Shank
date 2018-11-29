package com.lbenedetto.shank

import android.content.Context
import android.content.Intent
import android.util.SparseArray


abstract class ShankPresenter<ViewClass> {

    companion object {
        private val instances = SparseArray<ShankPresenter<*>>()
        private var globalIDSource: Int = 0

        @Synchronized
        fun register(id: Int, presenter: ShankPresenter<*>) {
            instances.put(id, presenter)
        }

        @Synchronized
        fun bind(id: Int, constructable: Constructable<*>){
            instances[id]!!.bind(constructable)
        }

        @Synchronized
        fun unregister(id: Int) {
            instances.remove(id)
        }

        @Synchronized
        fun getNextID(): Int {
            return globalIDSource++
        }
    }

    private lateinit var constructable: Constructable<ViewClass>
    private var id = getNextID()
    init {
        ShankPresenter.register(id, this)
    }

    fun start(context: Context, intent: Intent){
        intent.putExtra("AppCompatActivityPresenter_ID", id)
        context.startActivity(intent)
    }

    private fun bind(activity: Constructable<*>) {
        this.constructable = activity as Constructable<ViewClass>
        onReady()
    }

    fun getView(): ViewClass? {
        return constructable.getView()
    }

    abstract fun onReady()

}