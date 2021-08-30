package com.example.uikit.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import android.widget.Scroller
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils

/**
 * @ClassName LabelScrollView
 * @Author zhangHong
 * @Date 2021/8/30 14:56
 */
class LabelScrollView : ViewGroup {
    companion object {
        private val DEFAULT_HEIGHT = 300
    }

    private var scroller //弹性滑动对象，用于实现View的弹性滑动
            : Scroller? = null

    //分别记录上次滑动的坐标
    private var mLastX = 0

    //分别记录上次滑动的坐标（onINterceptTouchEvent）
    private var mLastXIntercept = 0
    private var mLastYIntercept = 0
    private var labelTextList: List<TextView>? = null
    private var textSize = 15.0f
    private var textHeight = 0
    private var maxWidth = 0
    private var column = 0
    private var maxLimit = 0
    private var mListener: LabelClickListener? = null
    private var selectCount = 0
    private var marginHorOffset = 0
    private var marginVerOffset = 0
    private var mMinimumVelocity = 0
    private var mMaximumVelocity = 0
    private var mVelocityTracker: VelocityTracker? = null
    private var labelData: List<LabelBean>? = null

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        scroller = Scroller(context)
        //获取最小和最大的移动距离
        val configuration = ViewConfiguration.get(context)
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity
    }

    fun selectCountIncrease() {
        selectCount++
        if (selectCount > maxLimit) {
            selectCount = maxLimit
        }
    }

    fun selectCountReduce() {
        selectCount--
        if (selectCount < 0) {
            selectCount = 0
        }
    }

    fun setLabelData(data: List<LabelBean>) {
        if (labelData == null) {
            this.labelData = listOf()
        }
        this.labelData = data
    }


    fun setTextSize(TextSize: Float) {
        textSize = TextSize
    }

    fun setMaxLimit(maxLimit: Int) {
        this.maxLimit = maxLimit
    }

    fun setSelectedCount(selectCount: Int) {
        this.selectCount = selectCount
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (labelData!!.isNotEmpty()) {
            labelTextList = ArrayList()
            for (labelDatum in labelData!!) {
                val textView = TextView(context)
                textView.text = labelDatum.str
                if (labelDatum.isSelect) {
                    textView.setBackgroundResource(labelDatum.selectedBackgroundResource)
                    textView.setTextColor(Color.parseColor(labelDatum.textSelectColor))
                } else {
                    textView.setBackgroundResource(labelDatum.normalBackgroundResource)
                    textView.setTextColor(Color.parseColor(labelDatum.textNormalColor))
                }
                textView.setPadding(
                    SizeUtils.dp2px(15f),
                    SizeUtils.dp2px(10f),
                    SizeUtils.dp2px(15f),
                    SizeUtils.dp2px(10f)
                )
                textView.textSize = textSize
                textView.tag = labelDatum
                textView.setOnClickListener { v: View ->
                    val tv = v as TextView
                    val labelBean = v.getTag() as LabelBean
                    if (!labelBean.isSelect) {
                        selectCount++
                        if (selectCount > maxLimit) {
                            selectCount = maxLimit
                            mListener!!.labelClick(null)
                            return@setOnClickListener
                        }
                        tv.setTextColor(Color.parseColor(labelBean.textSelectColor))
                        tv.setBackgroundResource(labelBean.selectedBackgroundResource)
                    } else {
                        selectCount--
                        if (selectCount < 0) {
                            selectCount = 0
                            mListener!!.labelClick(null)
                            return@setOnClickListener
                        }
                        tv.setTextColor(Color.parseColor(labelBean.textNormalColor))
                        tv.setBackgroundResource(labelBean.normalBackgroundResource)
                    }
                    labelBean.isSelect = !labelBean.isSelect
                    mListener?.labelClick(labelBean)
                }
                textView.gravity = Gravity.CENTER
                this.addView(textView)
                (labelTextList as ArrayList<TextView>).add(textView)
            }
            textHeight =
                ((labelTextList as ArrayList<TextView>)[0].paint.fontMetrics.descent - (labelTextList as ArrayList<TextView>)[0].paint.fontMetrics.ascent).toInt()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量整个高度
        val h: Int = measureHeight(heightMeasureSpec)
        setMeasuredDimension(measureWidth(h), h)
    }


    /**
     * 测量宽度
     */
    private fun measureWidth(h: Int): Int {
        var result = 0
        //需要绘制多少行   布局高度/(文本高度+paddingTop+paddingBottom+垂直方向间距)
        val row =
            h / (textHeight + labelTextList!![0].paddingTop + labelTextList!![0].paddingBottom + marginVerOffset)
        //每行需要绘制多少列
        column = Math.ceil((labelTextList!!.size.toFloat() / row.toFloat()).toDouble()).toInt()
        if (row * column < labelTextList!!.size) {
            val overage = labelTextList!!.size - row * column
            column += overage
        }
        for (j in 0 until row) {
            var countWidth = 0
            for (i in 0 until column) {
                if (j * row + i >= labelTextList!!.size) {
                    break
                }
                //获取每个textview
                val textView = labelTextList!![j * row + i]
                //测量文本宽度
                val textWidth = textView.paint.measureText(textView.text.toString())
                //测量textView控件的宽度  文本宽度+PaddingLeft+PaddingRight+水平方向间距
                val strWidth =
                    textWidth + textView.paddingLeft + textView.paddingRight + marginHorOffset
                //累计获取最大宽度
                countWidth += strWidth.toInt()
            }
            if (countWidth > result) {
                result = countWidth
            }
        }
        return result
    }

    /**
     * 测量高度
     */
    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result: Int
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode == MeasureSpec.EXACTLY) {
            result = heightSize
        } else {
            result = DEFAULT_HEIGHT
            if (heightMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, heightSize)
            }
        }
        return result
    }


    fun setLabelMarginHor(marginOffset: Int) {
        marginHorOffset = marginOffset
    }

    fun setLabelMarginVer(marginVerOffset: Int) {
        this.marginVerOffset = marginVerOffset
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount > 0) {
            val textView = labelTextList!![0]
            val paint = textView.paint
            var right = 0
            var left = 0
            var top = textView.paddingTop
            var bottom = textHeight + textView.paddingTop + textView.paddingBottom
            var columnIndex = 0
            //布局所有子控件
            for (index in 0 until childCount) {
                //获取子控件
                val childText = getChildAt(index) as TextView
                if (columnIndex >= column) {
                    //需要换行
                    if (maxWidth < right) {
                        maxWidth = right
                    }
                    top += textHeight + childText.paddingTop + childText.paddingBottom + marginVerOffset
                    bottom += textHeight + childText.paddingTop + childText.paddingBottom + marginVerOffset
                    left = 0
                    columnIndex = 0
                }
                val strWidth =
                    paint.measureText(childText.text.toString()) + childText.paddingLeft + childText.paddingRight
                right = if (index == 0 || columnIndex >= column) {
                    //绘制第一列
                    //子控件宽度 = 文字宽度+PaddingLeft+PaddingRight
                    //水平间距+子控件宽度
                    marginHorOffset + strWidth.toInt()
                    //下一个view的left位置
                } else {
                    //子控件宽度 = 文字宽度+PaddingLeft+PaddingRight
                    //上一个控件的右边+水平间距+文字宽度
                    left + marginHorOffset + strWidth.toInt()
                    //下一个view的left位置
                }
                childText.layout(marginHorOffset + left, top, right, bottom + textView.paddingTop)
                left = right
                columnIndex++
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN ->                 //如果停止滚动则取消动画（即手指按下就停止滚动）
                if (!scroller!!.isFinished) {
                    scroller!!.abortAnimation()
                }
            MotionEvent.ACTION_MOVE -> {
                //测试器添加移动事件
                mVelocityTracker?.addMovement(event)
                val deltaX = x - mLastX
                scrollBy(-deltaX, 0)
            }
            MotionEvent.ACTION_UP -> {
                //测试器添加抬起事件
                mVelocityTracker!!.addMovement(event)
                //添加加速度的测试时间，这里是测量1000毫秒内的加速度
                mVelocityTracker!!.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())
                //获取x方向加速度
                val pxsec = mVelocityTracker!!.xVelocity
                val maxScroll = maxWidth - ScreenUtils.getScreenWidth()
                if (Math.abs(pxsec) > mMinimumVelocity) scroller!!.fling(
                    scrollX, 0,
                    (-pxsec).toInt(), 0, 0, maxScroll, 0, 0
                )
                val scrollX = scrollX
                if (scrollX >= maxWidth - ScreenUtils.getScreenWidth()) {
                    val offset = maxWidth - ScreenUtils.getScreenWidth()
                    scroller!!.startScroll(
                        getScrollX(),
                        0,
                        offset - getScrollX() + marginHorOffset,
                        0,
                        500
                    ) //up 时自动滚动到
                } else if (scrollX <= 0) {
                    val dx = -getScrollX()
                    scroller!!.startScroll(getScrollX(), 0, dx, 0, 500) //up 时自动滚动到
                }
                invalidate()
                //清空测试器
                recycleVelocityTracker()
            }
            MotionEvent.ACTION_CANCEL -> recycleVelocityTracker()
        }
        mLastX = x
        return true
    }

    /**
     * 创建或复用加速度测试器
     */
    private fun initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        } else {
            mVelocityTracker!!.clear()
        }
    }

    /**
     * 回收加速度测试器，防止内存泄漏
     */
    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    fun setLabelClickListener(mListener: LabelClickListener?) {
        this.mListener = mListener
    }

    override fun computeScroll() {
        if (scroller!!.computeScrollOffset()) {
            scrollTo(scroller!!.currX, scroller!!.currY)
            postInvalidate()
        }
    }

    interface LabelClickListener {
        fun labelClick(bean: LabelBean?)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller!!.isFinished) {
                    scroller!!.abortAnimation()
                }
                initOrResetVelocityTracker()
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastXIntercept
                val deltaY = y - mLastYIntercept
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_CANCEL -> recycleVelocityTracker()
        }
        mLastX = x
        mLastXIntercept = x
        mLastYIntercept = y
        return super.onInterceptTouchEvent(ev)
    }

    fun updateAndScroll(index: Int) {
        val textView = labelTextList!![index]
        val rect = Rect()
        textView.getGlobalVisibleRect(rect)
        val labelBean = textView.tag as LabelBean
        val rect1 = Rect()
        textView.getLocalVisibleRect(rect1)
        textView.setBackgroundResource(labelBean.selectedBackgroundResource)
        textView.setTextColor(Color.parseColor(labelBean.textSelectColor))
        if (rect1.left != 0) {
            if (rect.left > maxWidth - SizeUtils.dp2px(200f)) {
                scroller!!.startScroll(
                    scrollX,
                    0,
                    maxWidth - ScreenUtils.getScreenWidth(),
                    0,
                    500
                ) //up 时自动滚动到
                return
            }
            scroller!!.startScroll(scrollX, 0, rect.left, 0, 500) //up 时自动滚动到
        }
        selectCountIncrease()
    }

    data class LabelBean(//内容
        var str: String, //字体颜色
        var textSelectColor: String,
        var textNormalColor: String,
        var normalBackgroundResource: Int,
        var selectedBackgroundResource: Int,
        var isSelect: Boolean
    )
}