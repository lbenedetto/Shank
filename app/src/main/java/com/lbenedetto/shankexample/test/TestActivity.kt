package com.lbenedetto.shankexample.test

import android.os.Bundle
import com.lbenedetto.shank.ConstructableActivity
import com.lbenedetto.shankexample.R
import com.lbenedetto.shankexample.R.id.textView
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : ConstructableActivity<TestView>() {
    private lateinit var testView: TestView

    override fun getView(): TestView {
        return testView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        testView = TestView(this)
        setContentView(testView)

        readyForPresenter()
    }


}