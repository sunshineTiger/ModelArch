package com.example.baselibrary.base

/**
 * @ClassName BaseBehavior
 * @Author zhangHong
 * @Date 2021/8/2 16:41
 */
interface BaseBehavior {
    //布局ID
    fun getLayoutId(): Int

    //初始化View
    fun initView()

    //初始化数据
    fun initData()
}