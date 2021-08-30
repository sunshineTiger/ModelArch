package com.example.uikit.utils.image

import com.bumptech.glide.request.RequestOptions
import com.example.uikit.R

/**
 * @ClassName GlideOptionsUtils
 * @Author zhangHong
 * @Date 2021/8/30 17:25
 */
class GlideOptionsUtils {
    companion object {
        fun baseOptions(): RequestOptions {
            return RequestOptions().placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
        }

        fun circleOptions(): RequestOptions {
            return baseOptions().circleCrop()
        }
    }
}