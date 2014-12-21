package com.ad.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * 
 * @类名称: LetterSlideBar
 * @描述: 字母排列的slidebar
 * @开发者: andy.xu
 * @时间: 2014-9-18 下午3:22:23
 * 
 */
public class LetterSlideBar extends View {
	public static final String TAG = LetterSlideBar.class.getName();
	private String[] letters;
	private OnLetterTouchListener letterTouchListener;
	private int mColor = Color.DKGRAY;

	public LetterSlideBar(Context context) {
		super(context);
		init(context);
	}

	public LetterSlideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LetterSlideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public void init(Context context) {

		letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
				"Z", "#", };
	}

	/**
	 * 
	 * @描述: 设置slidebar的字符数组
	 * @参数 @param letterArray
	 * @返回值 void
	 * @异常
	 */
	public void onSetLetter(final String[] letterArray) {
		letters = letterArray;
	}

	/**
	 * 
	 * @描述: 设置字体的颜色
	 * @参数 @param nColor
	 * @返回值 void
	 * @异常
	 */
	public void onSetTextColor(final int nColor) {
		mColor = nColor;
	}

	/**
	 * 每一项的高度
	 */
	private float itemHeight = -1;
	private Paint paint;
	private Bitmap letterBitmap;

	@Override
	protected void onDraw(Canvas canvas) {
		if (letters == null) {
			return;
		}
		if (itemHeight == -1) {
			itemHeight = getHeight() / letters.length;
		}
		if (paint == null) {
			// 初始化画笔
			paint = new Paint();
			paint.setTextSize(itemHeight - 4);
			// 字体颜色
			paint.setColor(mColor);
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);

			// 创建一张包含所有列表的图
			Canvas mCanvas = new Canvas();
			letterBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
			// Log.v(TAG, "width,height" + getMeasuredWidth() + ":" +
			// getMeasuredHeight());
			mCanvas.setBitmap(letterBitmap);
			float widthCenter = getMeasuredWidth() / 2.0F;
			// 画字符上图片中
			for (int i = 0; i < letters.length; i++) {
				mCanvas.drawText(letters[i], widthCenter - paint.measureText(letters[i]) / 2, itemHeight * i + itemHeight, paint);
			}
		}
		if (letterBitmap != null) {// 图片不为空就画图
			canvas.drawBitmap(letterBitmap, 0, 0, paint);
		}
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		if (letterTouchListener == null || letters == null) {
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			Log.v(TAG, "action down or move");
			int position = (int) (event.getY() / itemHeight + 1);
			if (position >= 0 && position < letters.length) {
				letterTouchListener.onLetterTouch(letters[position], position);
			}
			return true;
		case MotionEvent.ACTION_OUTSIDE:
		case MotionEvent.ACTION_UP:
			Log.v(TAG, "action up");
			letterTouchListener.onActionUp();
			return true;
		}
		return false;
	}

	/**
	 * 设置显示在边栏上的字母
	 * 
	 * @param letters
	 */
	public void setShowString(String[] letters) {
		this.letters = letters;
	}

	/**
	 * 设置点击某个字母的时候
	 * 
	 * @param listener
	 */
	public void setOnLetterTouchListener(OnLetterTouchListener listener) {
		this.letterTouchListener = listener;
	}

	public interface OnLetterTouchListener {

		/**
		 * 某个字母被按下的时候
		 * 
		 * @param letter
		 * @param position
		 */
		public void onLetterTouch(String letter, int position);

		/**
		 * 触控手指离开的时候
		 */
		public void onActionUp();
	}
}