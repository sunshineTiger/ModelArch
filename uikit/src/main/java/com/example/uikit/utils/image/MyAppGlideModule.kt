package com.example.uikit.utils.image

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import java.io.InputStream

/**
 * @ClassName AppGlideModule
 * @Author zhangHong
 * @Date 2021/8/13 19:030
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //获取最大可用内存
        val maxMemory = Runtime.getRuntime().maxMemory().toInt()
        //设置缓存的大小
        val cacheSize = maxMemory / 8
        //设置Bitmap的缓存池
        builder.setBitmapPool(LruBitmapPool(30))
        //设置内存缓存
        builder.setMemoryCache(LruResourceCache(cacheSize.toLong()))
        //设置磁盘缓存
        builder.setDiskCache(InternalCacheDiskCacheFactory(context))
        //设置读取不在缓存中资源的线程
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor())
        //设置读取磁盘缓存中资源的线程
        builder.setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor())
        //设置日志级别
        builder.setLogLevel(Log.VERBOSE)
        //设置全局选项 图片格式565，只缓存转换过后的图片。
        val requestOptions = RequestOptions().format(DecodeFormat.PREFER_RGB_565).diskCacheStrategy(
            DiskCacheStrategy.ALL
        )
        builder.setDefaultRequestOptions(requestOptions)
    }

    /**
     * 清单解析开启
     * 这里不开启，避免添加相同的modules两次
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    /**
     *  为App注册一个自定义的String类型的BaseGlideUrlLoader
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(
            String::class.java,
            InputStream::class.java,
            CustomBaseGlideUrlLoader.Factory()
        )
    }
}