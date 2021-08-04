package com.example.baselibrary.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

/**
 * @ClassName BaseActivity
 * @Author zhangHong
 * @Date 2021/8/2 16:36
 */
abstract class BaseActivity : AppCompatActivity(), BaseBehavior {
    companion object {
        const val SAVE_BUNDLE: String = "save_bundle_flag"
        val TAG: String = BaseActivity::class.java.name
    }

    private var savedBundle: Bundle? = null

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
    open fun getSaveBundle(): Bundle? {
        return null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (getSaveBundle() != null) {
            // 被销毁前缓存一些数据
            Log.e(TAG, String.format("%s %s", TAG, " onSaveInstanceState"))
            outState.putBundle(SAVE_BUNDLE, getSaveBundle())
        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 重新创建后恢复缓存的数据
        Log.e(TAG, String.format("%s %s", TAG, " onRestoreInstanceState"))
        savedBundle = savedInstanceState.getBundle(SAVE_BUNDLE)
    }
}
