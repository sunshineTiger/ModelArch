package com.example.baselibrary.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

/**
 * @ClassName BaseActivity
 * @Author zhangHong
 * @Date 2021/8/2 16:36
 */
abstract class BaseActivity : AppCompatActivity(), BaseBehavior {

    val SAVE_BUNDLE: String = "save_bundle_flag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeContentViewInit()
        setContentView(getLayoutId())
        initView()
        initData()
    }

    //需要在setContentView方法之前初始化一些数据和操作
    open fun beforeContentViewInit() {

    }

    @Nullable
    abstract fun getSaveBundle(): Bundle
    abstract fun getSaveMap()


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (getSaveBundle() != null) {
            outState.putBundle(SAVE_BUNDLE, getSaveBundle())
        }

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }
}
