package com.example.baselibrary.base

/**
 * @ClassName BaseBehavior
 * @Author zhangHong
 * @Date 2021/8/2 16:41
 */
interface BaseBehavior {
    fun getLayoutId(): Int
    fun initView()
    fun initData()
}