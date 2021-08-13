package com.example.uikit.utils.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @ClassName ImageUtil
 * @Author zhangHong
 * @Date 2021/8/13 18:40
 */
class ImageUtil {
    companion object {
        /**
         * 加载图片
         */
        fun imageLoad(context: Context, url: String, iv: ImageView, placeholder: Int, error: Int) {
            Glide.with(context.applicationContext)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .into(iv)
        }

        /**
         * 加载缩略图
         */
        fun imageLoad(context: Context, url: String, iv: ImageView, float: Float) {
            Glide.with(context.applicationContext)
                .load(url)
                .thumbnail(float)
                .into(iv)
        }
    }
}