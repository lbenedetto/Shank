package com.lbenedetto.basicshankexample.test

import android.os.Bundle
import com.lbenedetto.basicshankexample.R
import com.lbenedetto.shank.ConstructableActivity

class TestActivity : ConstructableActivity<TestContract.View>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setView(TestView(this))
    }
}