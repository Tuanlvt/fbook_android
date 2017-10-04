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
  private val mRectPath by lazy { Path() }
  private val mTextPath by lazy { Path() }
  private val mTextBound = Rect()
  private var mText: String? = ""
  private var mDistance = 0F
  private var mHeightLabel = resources.getDimension(R.dimen.dp_30)
  private var mDistanceWriteText = resources.getDimension(R.dimen.dp_20)

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

    mText?.let {
      mPaintText.getTextBounds(mText, 0, it.length, mTextBound)
    }
    mText?.let {
      canvas?.drawTextOnPath(mText, mTextPath, mDistanceWriteText,
          (mTextBound.height() / 2).toFloat(), mPaintText)
    }
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
  }

  private fun init() {
    mPaintBackground.isAntiAlias = true
    mPaintBackground.isDither = true
    mPaintBackground.color = ContextCompat.getColor(context, R.color.colorPrimary)

    mPaintText.isAntiAlias = true
    mPaintText.isDither = true
    mPaintText.strokeJoin = Paint.Join.ROUND
    mPaintText.strokeCap = Paint.Cap.SQUARE
    mPaintText.color = Color.WHITE
    mPaintText.textSize = resources.getDimension(R.dimen.sp_10)

  }

  fun setDistanceWriteText(distance: Int) {
    mDistanceWriteText = distance.toFloat()
  }

  fun setHeightLabel(height: Int) {
    mHeightLabel = height.toFloat()
  }

  fun setDistanceLabel(distance: Int) {
    if (distance == 1) {
      mDistance = resources.getDimension(R.dimen.dp_80)
    } else {
      mDistance = resources.getDimension(R.dimen.dp_60)
    }
  }

  fun updateText(text: String?) {
    mText = text
  }
}
