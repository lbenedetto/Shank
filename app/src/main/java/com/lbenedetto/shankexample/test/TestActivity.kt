package com.lbenedetto.shankexample.test

import android.os.Bundle
import com.lbenedetto.shank.ConstructableActivity
import com.lbenedetto.shankexample.R

class TestActivity : ConstructableActivity<TestContract.View>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setView(TestView(this))
    }

}