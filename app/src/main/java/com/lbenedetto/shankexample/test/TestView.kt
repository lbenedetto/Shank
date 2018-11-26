package com.lbenedetto.shankexample.test

import android.content.Context
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.lbenedetto.shankexample.R
import kotlinx.android.synthetic.main.activity_test.view.*

class TestView(context: Context) : CoordinatorLayout(context), TestContract.View {
    init {
        View.inflate(context, R.layout.activity_test, this)
    }

    override fun setText(text: String) {
        textView.text = text
    }
}