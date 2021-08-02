package com.example.basemodule.repository.localrepository

/**
 * @ClassName LocalRepository
 * @Author zhangHong
 * @Date 2021/8/2 11:05
 */
class LocalRepository {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            LocalRepository()
        }
    }
}