package com.lbenedetto.shankexample.test

import com.lbenedetto.shank.ShankPresenter

class TestPresenter(var text: String) : ShankPresenter<TestActivity, TestContract.View>(), TestContract.Presenter {
    private lateinit var view: TestContract.View

    override fun onReady() {
        view = getView()
        getView().setText(text)
    }
}