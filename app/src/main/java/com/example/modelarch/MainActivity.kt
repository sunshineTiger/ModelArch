package com.example.modelarch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baselibrary.base.BaseActivity
import com.example.basemodule.http.HttpManger

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

    }

    override fun initData() {
        SAVE_BUNDLE
    }
}