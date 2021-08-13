package com.example.baselibrary.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @ClassName BaseFragment
 * @Author zhangHong
 * @Date 2021/8/13 17:27
 */
abstract class BaseFragment : Fragment(), BaseBehavior {
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, isAttachToRoot(container))
    }

    protected open fun isAttachToRoot(container: ViewGroup?): Boolean {
        return container != null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}