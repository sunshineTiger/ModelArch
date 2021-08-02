package com.example.basemodule.repository.netrepository

import com.example.basemodule.repository.proxy.RepositoryProxy

/**
 * @ClassName NetRepository
 * @Author zhangHong
 * @Date 2021/8/2 11:05
 */
class NetRepository {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            NetRepository()
        }
    }
}