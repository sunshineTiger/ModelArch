package com.example.uikit.utils.logger

import android.util.Log
import androidx.annotation.IntDef
import com.example.uikit.BuildConfig
import org.jetbrains.annotations.NotNull

/**
 * @ClassName Logger
 * @Author zhangHong
 * @Date 2021/8/13 18:02
 */
class LoggerUtil {
    companion object {
        private fun log(@NotNull tag: String, @NotNull message: String, @LevelType type: Int) {
            when (type) {
                LevelType.LEVEL_DEBUG -> {
                    Log.d(tag, message)
                }
                LevelType.LEVEL_INFO -> {
                    Log.i(tag, message)
                }
                LevelType.LEVEL_WARNING -> {
                    Log.w(tag, message)
                }
                LevelType.LEVEL_ERROR -> {
                    Log.e(tag, message)
                }
                LevelType.LEVEL_VERBOSE -> {
                    Log.v(tag, message)
                }
            }
        }

        fun logDebug(@NotNull tag: String, @NotNull message: String) {
            if (!BuildConfig.DEBUG) {
                return
            }
            log(tag, message, LevelType.LEVEL_DEBUG)
        }

        fun logError(@NotNull tag: String, @NotNull message: String) {
            if (!BuildConfig.DEBUG) {
                return
            }
            log(tag, message, LevelType.LEVEL_ERROR)
        }

        fun logWarning(@NotNull tag: String, @NotNull message: String) {
            if (!BuildConfig.DEBUG) {
                return
            }
            log(tag, message, LevelType.LEVEL_WARNING)
        }

        fun logInfo(@NotNull tag: String, @NotNull message: String) {
            if (!BuildConfig.DEBUG) {
                return
            }
            log(tag, message, LevelType.LEVEL_INFO)
        }

        fun logVerbose(@NotNull tag: String, @NotNull message: String) {
            if (!BuildConfig.DEBUG) {
                return
            }
            log(tag, message, LevelType.LEVEL_VERBOSE)
        }

        @IntDef(
            LevelType.LEVEL_DEBUG,
            LevelType.LEVEL_INFO,
            LevelType.LEVEL_WARNING,
            LevelType.LEVEL_ERROR,
            LevelType.LEVEL_VERBOSE,
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class LevelType {
            companion object {
                const val LEVEL_DEBUG: Int = 1
                const val LEVEL_INFO: Int = 2
                const val LEVEL_WARNING: Int = 3
                const val LEVEL_ERROR: Int = 4
                const val LEVEL_VERBOSE: Int = 5
            }
        }
    }
}