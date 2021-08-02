package com.example.basemodule.repository.proxy

/**
 * @ClassName RepositoryProxy
 * @Author zhangHong
 * @Date 2021/8/2 11:06
 */
class RepositoryProxy {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            RepositoryProxy()
        }
    }
}