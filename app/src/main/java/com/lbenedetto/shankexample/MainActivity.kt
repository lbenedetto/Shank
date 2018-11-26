package com.lbenedetto.shankexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lbenedetto.shankexample.test.TestActivity
import com.lbenedetto.shankexample.test.TestPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            TestPresenter(editText.text.toString())
                .start(this, TestActivity::class.java)
        }
    }
}
