package com.example.uikit.base

import android.app.Application
import com.blankj.utilcode.util.Utils

/**
 * @ClassName BaseApplication
 * @Author zhangHong
 * @Date 2021/8/16 11:17
 */
abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}