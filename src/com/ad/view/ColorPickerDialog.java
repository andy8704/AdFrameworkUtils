package com.ad.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.ad.util.DisplayUtil;
import com.ad.view.ColorPickerView.ColorPickerListener;
import com.example.adframeworkutil.R;

/**
 * 颜色选择器
 * 
 * @author andy.xu
 * 
 */
public class ColorPickerDialog extends Dialog {
	
	// 基本7中颜色定义
	// 黑色
	public static final int COLOR_BLACK = 0xff000000;
	// 红色
	public static final int COLOR_RED = 0xffff4c4c;
	// 黄色
	public static final int COLOR_YELLOW = 0xffeaff3b;
	// 绿色
	public static final int COLOR_GREEN = 0xff3ed833;
	// 浅蓝
	public static final int COLOR_CYAN = 0xff00d2ff;
	// 蓝色
	public static final int COLOR_BLUE = 0xff336af6;
	// 紫色
	public static final int COLOR_PURPLE = 0xffb555ff;

	Context context;
	private String title;// 标题
	private int mInitialColor;// 初始颜色
	private OnColorChangedListener mListener;

	private ImageView mBlackColorBtn = null;
	private ImageView mRedColorBtn = null;
	private ImageView mYellowColorBtn = null;
	private ImageView mGreenColorBtn = null;
	private ImageView mCyanColorBtn = null;
	private ImageView mBlueColorBtn = null;
	private ImageView mPurpleColorBtn = null;
	private ColorPickerView mColorPickerView = null;

	private Button mOkBtn = null;
	private Button mCancelBtn = null;

	/**
	 * 初始颜色黑色
	 * 
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param listener
	 *            回调
	 */
	public ColorPickerDialog(Context context, String title, OnColorChangedListener listener) {
		this(context, Color.BLACK, title, listener);
	}

	/**
	 * 
	 * @param context
	 * @param initialColor
	 *            初始颜色
	 * @param title
	 *            标题
	 * @param listener
	 *            回调
	 */
	public ColorPickerDialog(Context context, int initialColor, String title, OnColorChangedListener listener) {
		super(context);
		this.context = context;
		mListener = listener;
		mInitialColor = initialColor;
		this.title = title;
	}

	public ColorPickerDialog(Context context, int initialColor, String title, int theme, OnColorChangedListener listener) {
		super(context, theme);
		this.context = context;
		mListener = listener;
		mInitialColor = initialColor;
		this.title = title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		WindowManager manager = getWindow().getWindowManager();
//		int height = (int) (manager.getDefaultDisplay().getHeight() * 0.5f);
//		int width = (int) (manager.getDefaultDisplay().getWidth() * 0.7f);
		// ColorPickerView myView = new ColorPickerView(context, height, width);
		setContentView(R.layout.scrawl_color_picker_tool_dlg);
		resize();
		
		initUI();
		setTitle(title);

		addListener();
	}
	
	private void resize() {
		WindowManager.LayoutParams p = getWindow().getAttributes();
		p.width = (int) (0.9 * DisplayUtil.getScreenWidth(context));
		getWindow().setAttributes(p);
	}

	private void initUI() {
		mColorPickerView = (ColorPickerView) findViewById(R.id.color_picker_view);
		mBlackColorBtn = (ImageView) findViewById(R.id.color_black_id);
		mRedColorBtn = (ImageView) findViewById(R.id.color_red_id);
		mYellowColorBtn = (ImageView) findViewById(R.id.color_yellow_id);
		mGreenColorBtn = (ImageView) findViewById(R.id.color_green_id);
		mCyanColorBtn = (ImageView) findViewById(R.id.color_cyan_id);
		mBlueColorBtn = (ImageView) findViewById(R.id.color_blue_id);
		mPurpleColorBtn = (ImageView) findViewById(R.id.color_purple_id);

		mOkBtn = (Button) findViewById(R.id.ok_btn_id);
		mCancelBtn = (Button) findViewById(R.id.cancel_btn_id);

		mOkBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (null != mListener)
					mListener.colorChanged(mColorPickerView.getCurColor());
				dismiss();
			}
		});

		mCancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		initCurColor();
	}

	private void initCurColor() {

		mColorPickerView.initCurColor(mInitialColor);
	}

	private void setCurSelect(final ImageView view) {

		int nColor = Color.BLACK;
//		switch (view.getId()) {
//		case R.id.color_black_id:
//			nColor = Color.BLACK;
//			break;
//		case R.id.color_red_id:
//			nColor = Color.RED;
//			break;
//		case R.id.color_yellow_id:
//			nColor = Color.YELLOW;
//			break;
//		case R.id.color_green_id:
//			nColor = Color.GREEN;
//			break;
//		case R.id.color_cyan_id:
//			nColor = Color.CYAN;
//			break;
//		case R.id.color_blue_id:
//			nColor = Color.BLUE;
//			break;
//		case R.id.color_purple_id:
//			nColor = COLOR_PURPLE;
//			break;
//		}
		setCurColor(nColor);
	}

	private void addListener() {

		mColorPickerView.setOnColorChangeListener(new ColorPickerListener() {
			@Override
			public void onColorChange(int nColor) {
				if (null != mListener)
					mListener.colorChanged(nColor);
				dismiss();
			}
		});

		mBlackColorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurSelect(mBlackColorBtn);
			}
		});

		mRedColorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurSelect(mRedColorBtn);
			}
		});

		mYellowColorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurSelect(mYellowColorBtn);
			}
		});

		mGreenColorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurSelect(mGreenColorBtn);
			}
		});

		mCyanColorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurSelect(mCyanColorBtn);
			}
		});

		mBlueColorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurSelect(mBlueColorBtn);
			}
		});

		mPurpleColorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurSelect(mPurpleColorBtn);
			}
		});
	}

	private void setCurColor(int nColor) {

		mColorPickerView.initCurColor(nColor);
	}

	// public class ColorPickerView extends View {
	// private Paint mPaint;// 渐变色环画笔
	// private Paint mCenterPaint;// 中间圆画笔
	// private Paint mLinePaint;// 分隔线画笔
	// private Paint mRectPaint;// 渐变方块画笔
	//
	// private Shader rectShader;// 渐变方块渐变图像
	// private float rectLeft;// 渐变方块左x坐标
	// private float rectTop;// 渐变方块右x坐标
	// private float rectRight;// 渐变方块上y坐标
	// private float rectBottom;// 渐变方块下y坐标
	//
	// private final int[] mCircleColors;// 渐变色环颜色
	// private final int[] mRectColors;// 渐变方块颜色
	//
	// private int mHeight;// View高
	// private int mWidth;// View宽
	// private float r;// 色环半径(paint中部)
	// private float centerRadius;// 中心圆半径
	//
	// private boolean downInCircle = true;// 按在渐变环上
	// private boolean downInRect;// 按在渐变方块上
	// private boolean highlightCenter;// 高亮
	// private boolean highlightCenterLittle;// 微亮
	//
	// public ColorPickerView(Context context) {
	// super(context);
	// this.mHeight = 300 - 36;
	// this.mWidth = 320;
	// setMinimumHeight(300 - 36);
	// setMinimumWidth(320);
	//
	// // 渐变色环参数
	// mCircleColors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,
	// 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
	// Shader s = new SweepGradient(0, 0, mCircleColors, null);
	// mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// mPaint.setShader(s);
	// mPaint.setStyle(Paint.Style.STROKE);
	// mPaint.setStrokeWidth(50);
	// r = mWidth / 2 * 0.6f - mPaint.getStrokeWidth() * 0.5f;
	//
	// // 中心方形
	// mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// mCenterPaint.setColor(mInitialColor);
	// mCenterPaint.setStrokeWidth(5);
	// centerRadius = (r - mPaint.getStrokeWidth() / 2) * 0.6f;
	//
	// // 边框参数
	// mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// mLinePaint.setColor(Color.parseColor("#72A1D1"));
	// mLinePaint.setStrokeWidth(4);
	//
	// // 黑白渐变参数
	// mRectColors = new int[] { 0xFF000000, mCenterPaint.getColor(), 0xFFFFFFFF
	// };
	// mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// mRectPaint.setStrokeWidth(5);
	// rectLeft = -r - mPaint.getStrokeWidth() * 1f;
	// rectTop = r + mPaint.getStrokeWidth() * 0.5f +
	// mLinePaint.getStrokeMiter() * 0.5f + 15;
	// rectRight = r + mPaint.getStrokeWidth() * 1f;
	// rectBottom = rectTop + 50;
	// }
	//
	// @Override
	// protected void onDraw(Canvas canvas) {
	// // 移动中心
	// canvas.translate(mWidth / 2, mHeight / 2 - 50);
	// // 画中方形
	// // canvas.drawCircle(0, 0, centerRadius, mCenterPaint);
	// canvas.drawRect(new RectF(-centerRadius, -centerRadius, centerRadius,
	// centerRadius), mCenterPaint);
	// // 是否显示中心圆外的小圆环
	// if (highlightCenter || highlightCenterLittle) {
	// int c = mCenterPaint.getColor();
	// mCenterPaint.setStyle(Paint.Style.STROKE);
	// if (highlightCenter) {
	// mCenterPaint.setAlpha(0xFF);
	// } else if (highlightCenterLittle) {
	// mCenterPaint.setAlpha(0x90);
	// }
	// canvas.drawCircle(0, 0, centerRadius + mCenterPaint.getStrokeWidth(),
	// mCenterPaint);
	//
	// mCenterPaint.setStyle(Paint.Style.FILL);
	// mCenterPaint.setColor(c);
	// }
	// // 画色环
	// canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
	// // 画黑白渐变块
	// if (downInCircle) {
	// mRectColors[1] = mCenterPaint.getColor();
	// }
	// rectShader = new LinearGradient(rectLeft, 0, rectRight, 0, mRectColors,
	// null, Shader.TileMode.MIRROR);
	// mRectPaint.setShader(rectShader);
	// canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, mRectPaint);
	// float offset = mLinePaint.getStrokeWidth() / 2;
	// canvas.drawLine(rectLeft - offset, rectTop - offset * 2, rectLeft -
	// offset, rectBottom + offset * 2, mLinePaint);// 左
	// canvas.drawLine(rectLeft - offset * 2, rectTop - offset, rectRight +
	// offset * 2, rectTop - offset, mLinePaint);// 上
	// canvas.drawLine(rectRight + offset, rectTop - offset * 2, rectRight +
	// offset, rectBottom + offset * 2, mLinePaint);// 右
	// canvas.drawLine(rectLeft - offset * 2, rectBottom + offset, rectRight +
	// offset * 2, rectBottom + offset, mLinePaint);// 下
	// super.onDraw(canvas);
	// }
	//
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// float x = event.getX() - mWidth / 2;
	// float y = event.getY() - mHeight / 2 + 50;
	// boolean inCircle = inColorCircle(x, y, r + mPaint.getStrokeWidth() / 2, r
	// - mPaint.getStrokeWidth() / 2);
	// boolean inCenter = inCenter(x, y, centerRadius);
	// boolean inRect = inRect(x, y);
	//
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// downInCircle = inCircle;
	// downInRect = inRect;
	// highlightCenter = inCenter;
	// case MotionEvent.ACTION_MOVE:
	// if (downInCircle && inCircle) {// down按在渐变色环内, 且move也在渐变色环内
	// float angle = (float) Math.atan2(y, x);
	// float unit = (float) (angle / (2 * Math.PI));
	// if (unit < 0) {
	// unit += 1;
	// }
	// mCenterPaint.setColor(interpCircleColor(mCircleColors, unit));
	// if (debug)
	// Log.v(TAG, "色环内, 坐标: " + x + "," + y);
	// } else if (downInRect && inRect) {// down在渐变方块内, 且move也在渐变方块内
	// mCenterPaint.setColor(interpRectColor(mRectColors, x));
	// }
	// if (debug)
	// Log.v(TAG, "[MOVE] 高亮: " + highlightCenter + "微亮: " +
	// highlightCenterLittle + " 中心: " + inCenter);
	// if ((highlightCenter && inCenter) || (highlightCenterLittle && inCenter))
	// {// 点击中心圆,
	// // 当前移动在中心圆
	// highlightCenter = true;
	// highlightCenterLittle = false;
	// } else if (highlightCenter || highlightCenterLittle) {// 点击在中心圆,
	// // 当前移出中心圆
	// highlightCenter = false;
	// highlightCenterLittle = true;
	// } else {
	// highlightCenter = false;
	// highlightCenterLittle = false;
	// }
	// invalidate();
	// break;
	// case MotionEvent.ACTION_UP:
	// if (highlightCenter && inCenter) {// 点击在中心圆, 且当前启动在中心圆
	// if (mListener != null) {
	// mListener.colorChanged(mCenterPaint.getColor());
	// ColorPickerDialog.this.dismiss();
	// }
	// }
	// if (downInCircle) {
	// downInCircle = false;
	// }
	// if (downInRect) {
	// downInRect = false;
	// }
	// if (highlightCenter) {
	// highlightCenter = false;
	// }
	// if (highlightCenterLittle) {
	// highlightCenterLittle = false;
	// }
	// invalidate();
	// break;
	// }
	// return true;
	// }
	//
	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// super.onMeasure(mWidth, mHeight);
	// }
	//
	// /**
	// * 坐标是否在色环上
	// *
	// * @param x
	// * 坐标
	// * @param y
	// * 坐标
	// * @param outRadius
	// * 色环外半径
	// * @param inRadius
	// * 色环内半径
	// * @return
	// */
	// private boolean inColorCircle(float x, float y, float outRadius, float
	// inRadius) {
	// double outCircle = Math.PI * outRadius * outRadius;
	// double inCircle = Math.PI * inRadius * inRadius;
	// double fingerCircle = Math.PI * (x * x + y * y);
	// if (fingerCircle < outCircle && fingerCircle > inCircle) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	//
	// /**
	// * 坐标是否在中心圆上
	// *
	// * @param x
	// * 坐标
	// * @param y
	// * 坐标
	// * @param centerRadius
	// * 圆半径
	// * @return
	// */
	// private boolean inCenter(float x, float y, float centerRadius) {
	// double centerCircle = Math.PI * centerRadius * centerRadius;
	// double fingerCircle = Math.PI * (x * x + y * y);
	// if (fingerCircle < centerCircle) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	//
	// /**
	// * 坐标是否在渐变色中
	// *
	// * @param x
	// * @param y
	// * @return
	// */
	// private boolean inRect(float x, float y) {
	// if (x <= rectRight && x >= rectLeft && y <= rectBottom && y >= rectTop) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	//
	// /**
	// * 获取圆环上颜色
	// *
	// * @param colors
	// * @param unit
	// * @return
	// */
	// private int interpCircleColor(int colors[], float unit) {
	// if (unit <= 0) {
	// return colors[0];
	// }
	// if (unit >= 1) {
	// return colors[colors.length - 1];
	// }
	//
	// float p = unit * (colors.length - 1);
	// int i = (int) p;
	// p -= i;
	//
	// // now p is just the fractional part [0...1) and i is the index
	// int c0 = colors[i];
	// int c1 = colors[i + 1];
	// int a = ave(Color.alpha(c0), Color.alpha(c1), p);
	// int r = ave(Color.red(c0), Color.red(c1), p);
	// int g = ave(Color.green(c0), Color.green(c1), p);
	// int b = ave(Color.blue(c0), Color.blue(c1), p);
	//
	// return Color.argb(a, r, g, b);
	// }
	//
	// /**
	// * 获取渐变块上颜色
	// *
	// * @param colors
	// * @param x
	// * @return
	// */
	// private int interpRectColor(int colors[], float x) {
	// int a, r, g, b, c0, c1;
	// float p;
	// if (x < 0) {
	// c0 = colors[0];
	// c1 = colors[1];
	// p = (x + rectRight) / rectRight;
	// } else {
	// c0 = colors[1];
	// c1 = colors[2];
	// p = x / rectRight;
	// }
	// a = ave(Color.alpha(c0), Color.alpha(c1), p);
	// r = ave(Color.red(c0), Color.red(c1), p);
	// g = ave(Color.green(c0), Color.green(c1), p);
	// b = ave(Color.blue(c0), Color.blue(c1), p);
	// return Color.argb(a, r, g, b);
	// }
	//
	// private int ave(int s, int d, float p) {
	// return s + Math.round(p * (d - s));
	// }
	// }

	/**
	 * 回调接口
	 * 
	 * 
	 */
	public interface OnColorChangedListener {
		/**
		 * 回调函数
		 * 
		 * @param color
		 *            选中的颜色
		 */
		void colorChanged(int color);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getmInitialColor() {
		return mInitialColor;
	}

	public void setmInitialColor(int mInitialColor) {
		this.mInitialColor = mInitialColor;
	}

	public OnColorChangedListener getmListener() {
		return mListener;
	}

	public void setmListener(OnColorChangedListener mListener) {
		this.mListener = mListener;
	}
}
