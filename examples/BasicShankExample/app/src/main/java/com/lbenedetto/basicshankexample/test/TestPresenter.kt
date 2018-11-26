package com.lbenedetto.basicshankexample.test

import com.lbenedetto.shank.ShankPresenter

class TestPresenter(var text: String) : ShankPresenter<TestActivity, TestContract.View>(), TestContract.Presenter {
    private lateinit var view: TestContract.View

    override fun onReady() {
        view = getView()!!
        view.setText(text)
    }
}