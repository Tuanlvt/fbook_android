package com.framgia.fbook.widget.view

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.framgia.fbook.R


/**
 * Created by Hyperion on 9/25/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ImageViewLabel : ImageView {

  private val mPaintBackground by lazy { Paint() }
  private val mPaintText by lazy { Paint() }
  private val mPaintStroke by lazy { Paint() }
  private val mRectPath by lazy { Path() }
  private val mTextPath by lazy { Path() }
  private val mTextBound = Rect()
  private var mText: String? = ""

  companion object {
    private val mDistance = 120F
    private val mHeightLabel = 60F
  }

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr) {
    init()
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    val startPosX = measuredWidth - mDistance - mHeightLabel
    val endPosX = measuredWidth
    val middle = mHeightLabel / 2
    mRectPath.reset()
    mRectPath.moveTo(startPosX, 0F)
    mRectPath.lineTo(startPosX + mHeightLabel, 0F)
    mRectPath.lineTo(endPosX.toFloat(), mDistance)
    mRectPath.lineTo(endPosX.toFloat(), mDistance + mHeightLabel)
    mRectPath.close()

    mTextPath.reset()
    mTextPath.moveTo(startPosX + middle, 0F)
    mTextPath.lineTo(endPosX.toFloat(), mDistance + middle)
    mTextPath.close()
    canvas?.drawPath(mRectPath, mPaintBackground)
    canvas?.drawPath(mRectPath, mPaintStroke)

    mText?.let {
      mPaintText.getTextBounds(mText, 0, it.length, mTextBound)
    }
    val actualDistance = mDistance + mHeightLabel / 2
    var begin_w_offset = 1.4142135f * actualDistance / 2 - mTextBound.width() / 2
    if (begin_w_offset < 0) begin_w_offset = 0f
    mText?.let {
      canvas?.drawTextOnPath(mText, mTextPath, begin_w_offset, (mTextBound.height() / 2).toFloat(),
          mPaintText)
    }
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
  }

  private fun init() {
    mPaintBackground.isAntiAlias = true
    mPaintBackground.isDither = true
    mPaintBackground.color = ContextCompat.getColor(context, R.color.colorPrimary)

    mPaintStroke.isDither = true
    mPaintStroke.isAntiAlias = true
    mPaintStroke.style = Paint.Style.STROKE

    mPaintText.isAntiAlias = true
    mPaintText.isDither = true
    mPaintText.strokeJoin = Paint.Join.ROUND
    mPaintText.strokeCap = Paint.Cap.SQUARE
    mPaintText.color = Color.WHITE
    mPaintText.textSize = resources.getDimension(R.dimen.sp_10)

  }

  fun updateText(text: String?) {
    mText = text
  }
}
