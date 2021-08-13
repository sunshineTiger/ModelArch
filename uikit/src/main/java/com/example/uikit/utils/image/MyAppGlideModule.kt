package com.example.uikit.utils.image

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.module.AppGlideModule

/**
 * @ClassName AppGlideModule
 * @Author zhangHong
 * @Date 2021/8/13 19:03
 */

class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }
}