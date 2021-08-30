package com.example.uikit.utils.image

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @ClassName OkHttpUrlLoader
 * @Author zhangHong
 * @Date 2021/8/30 17:59
 */
class CustomBaseGlideUrlLoader(
    concreteLoader: ModelLoader<GlideUrl, InputStream>?,
    modelCache: ModelCache<String, GlideUrl>
) :
    BaseGlideUrlLoader<String>(concreteLoader, modelCache) {
    companion object {
        val urlCache = ModelCache<String, GlideUrl>(150)
    }

    /**
     * Url的匹配规则
     */
    private val PATTERN: Pattern = Pattern.compile("__w-((?:-?\\d+)+)__")

    override fun handles(model: String): Boolean {
        return true
    }

    /**
     * If the URL contains a special variable width indicator (eg "__w-200-400-800__")
     * we get the buckets from the URL (200, 400 and 800 in the example) and replace
     * the URL with the best bucket for the requested width (the bucket immediately
     * larger than the requested width).
     *
     * 控制加载的图片的大小
     */
    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        val m: Matcher = PATTERN.matcher(model)
        var bestBucket = 0
        if (m.find()) {
            val found = m.group(1)!!.split("-").toTypedArray()
            for (bucketStr in found) {
                bestBucket = Integer.parseInt(bucketStr)
                if (bestBucket >= width) {
                    // the best bucket is the first immediately bigger than the requested width
                    break
                }
            }
            if (bestBucket > 0) {
                return m.replaceFirst("w" + bestBucket)
            }
        }
        return model
    }

    /**
     * 工厂来构建CustormBaseGlideUrlLoader对象
     */
    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return CustomBaseGlideUrlLoader(
                multiFactory.build(
                    GlideUrl::class.java,
                    InputStream::class.java
                ), urlCache
            )
        }

        override fun teardown() {

        }
    }
}