package com.ad.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;

/**
 * 
 * 
 * @类名称: DrawableStateUtil
 * @描述: 控件不同状态下的背景色
 * @开发者: andy.xu
 * @时间: 2014-8-19 上午9:40:49
 * 
 */
public class DrawableStateUtil {
	// 以下这个方法也可以把你的图片数组传过来，以StateListDrawable来设置图片状态，来表现button的各中状态。未选
	// 中，按下，选中效果。

	/**
	 * 获取一个控件的按下，弹起的状态的背景色
	 * 
	 * @param normalPicPath
	 *            [in] 正常状态背景图片的路径
	 * @param pressedPicPath
	 *            [in] 按下状态背景图片的路径
	 * @return 状态的背景列表
	 */
	public static StateListDrawable getSelectBgFromFile(final String normalPicPath, final String pressedPicPath) {
		if (TextUtils.isEmpty(normalPicPath))
			return null;

		StateListDrawable bg = new StateListDrawable();
		if (!TextUtils.isEmpty(pressedPicPath)) {
			Drawable pressed = Drawable.createFromPath(pressedPicPath);
			if (null != pressed) {
				bg.addState(new int[] { android.R.attr.state_pressed }, pressed);
			}
		}

		Drawable normal = Drawable.createFromPath(normalPicPath);
		if (null != normal)
			bg.addState(new int[] {}, normal);
		return bg;
	}

	/**
	 * 获取一个控件的按下，弹起的状态的背景色
	 * 
	 * @param normalPicStream
	 *            [in] 正常状态背景图片的数据流
	 * @param pressedPicStream
	 *            [in]按下状态背景图片的数据流
	 * @return 状态的背景列表
	 */
	public static StateListDrawable getSelectBgFromStream(final InputStream normalPicStream, final InputStream pressedPicStream) {
		if (null == normalPicStream || null == pressedPicStream)
			return null;
		StateListDrawable bg = new StateListDrawable();
		Drawable pressed = Drawable.createFromStream(pressedPicStream, "pressedPic");
		if (null != pressed) {
			bg.addState(new int[] { android.R.attr.state_pressed }, pressed);
		}

		Drawable normal = Drawable.createFromStream(normalPicStream, "normalPic");
		if (null != normal)
			bg.addState(new int[] {}, normal);
		return bg;
	}

	/**
	 * 获取一个控件的按下，正常的状态的背景色
	 * 
	 * @param context
	 *            [in]
	 * @param nNormalDrawableId
	 *            [in] 正常状态的drawabel资源Id
	 * @param nPressedDrawableId
	 *            [in] 按下状态的drawable资源Id
	 * @return 状态的背景列表
	 */
	public static StateListDrawable getSelectBg(final Context context, final int nNormalDrawableId, final int nPressedDrawableId) {

		if (null == context || 0 == nNormalDrawableId)
			return null;

		StateListDrawable bg = new StateListDrawable();
		Drawable normal = context.getResources().getDrawable(nNormalDrawableId);

		Drawable pressed = null;
		if (0 != nPressedDrawableId) {
			pressed = context.getResources().getDrawable(nPressedDrawableId);
		}

		if (null != pressed) {
			bg.addState(new int[] { android.R.attr.state_pressed }, pressed);
		}

		if (null != normal) {
			bg.addState(new int[] {}, normal);
		}
		return bg;
	}

	/**
	 * 获取一个控件的按下，正常的状态的背景色
	 * 
	 * @param context
	 *            [in] 设备上下文
	 * @param normalPicPath
	 *            [in] 正常状态的图片的路径
	 * @param pressedPicPath
	 *            [in] 按下状态的图片的路径
	 * @param nNormalDrawableId
	 *            [in] 正常状态的drawable资源
	 * @param nPressedDrawableId
	 *            [in] 按下状态的drawable资源
	 * @return
	 */
	public static StateListDrawable getSelectBgFromFile(final Context context, final String normalPicPath, final String pressedPicPath,
			final int nNormalDrawableId, final int nPressedDrawableId) {
		if (null == context)
			return null;

		StateListDrawable bg = null;
		Drawable pressed = Drawable.createFromPath(pressedPicPath);
		Drawable normal = Drawable.createFromPath(normalPicPath);
		if (null != pressed && null != normal) {
			bg = new StateListDrawable();
			bg.addState(new int[] { android.R.attr.state_pressed }, pressed);
			bg.addState(new int[] {}, normal);
		} else {
			return getSelectBg(context, nNormalDrawableId, nNormalDrawableId);
		}
		return bg;
	}

	/**
	 * 获取一个控件的按下，正常的状态的背景色
	 * 
	 * @param context
	 *            [in] 设备上下文
	 * @param normalPicPath
	 *            [in] 正常图片的路径
	 * @param pressedPicPath
	 *            [in] 按下图片的路径
	 * @param nDefaultSelectId
	 *            [in] 默认的select资源
	 * @return
	 */
	public static StateListDrawable getSelectBgFromFile(final Context context, final String normalPicPath, final String pressedPicPath,
			final int nDefaultSelectId) {
		if (null == context)
			return null;

		StateListDrawable bg = null;
		Drawable pressed = Drawable.createFromPath(pressedPicPath);
		Drawable normal = Drawable.createFromPath(normalPicPath);
		if (null != pressed && null != normal) {
			bg = new StateListDrawable();
			bg.addState(new int[] { android.R.attr.state_pressed }, pressed);
			bg.addState(new int[] {}, normal);
		} else {
			return (StateListDrawable) context.getResources().getDrawable(nDefaultSelectId);
		}
		return bg;
	}

	/**
	 * 获取一个控件的按下，正常的状态的背景色
	 * 
	 * @param context
	 *            [in] 设备上下文
	 * @param normalPicStream
	 *            [in] 正常状态的图片的数据流
	 * @param pressedPicStream
	 *            [in] 按下状态的图片的数据流
	 * @param nDefaultSelectId
	 *            [in] 默认控件的select
	 * @return
	 */
	public static StateListDrawable getSelectBgFromStream(final Context context, final InputStream normalPicStream, final InputStream pressedPicStream,
			final int nDefaultSelectId) {

		if (null == context)
			return null;

		StateListDrawable bg = null;
		Drawable pressed = Drawable.createFromStream(pressedPicStream, "pressedPic");
		Drawable normal = Drawable.createFromStream(normalPicStream, "normalPic");
		if (null != pressed && null != normal) {
			bg = new StateListDrawable();
			bg.addState(new int[] { android.R.attr.state_pressed }, pressed);
			bg.addState(new int[] {}, normal);
		} else {
			return (StateListDrawable) context.getResources().getDrawable(nDefaultSelectId);
		}
		return bg;
	}

	/**
	 * 获取一个控件的按下，正常的状态的背景色
	 * 
	 * @param context
	 *            [in] 设备上下文
	 * @param normalPicStream
	 *            [in] 正常状态的图片的数据流
	 * @param pressedPicStream
	 *            [in] 按下状态的图片的数据流
	 * @param nNormalDrawableId
	 *            [in] 正常状态的drawable资源
	 * @param nPressedDrawableId
	 *            [in] 按下状态的drawable资源
	 * @return
	 */
	public static StateListDrawable getSelectBgFromStream(final Context context, final InputStream normalPicStream, final InputStream pressedPicStream,
			final int nNormalDrawableId, final int nPressedDrawableId) {

		if (null == context)
			return null;

		StateListDrawable bg = null;
		Drawable pressed = Drawable.createFromStream(pressedPicStream, "pressedPic");
		Drawable normal = Drawable.createFromStream(normalPicStream, "normalPic");
		if (null != pressed && null != normal) {
			bg = new StateListDrawable();
			bg.addState(new int[] { android.R.attr.state_pressed }, pressed);
			bg.addState(new int[] {}, normal);
		} else {
			return getSelectBg(context, nNormalDrawableId, nNormalDrawableId);
		}
		return bg;
	}

	/**
	 * 获取控件的背景
	 * 
	 * @param context
	 *            [in] 设备上下文
	 * @param bgPicPath
	 *            [in] 背景图片的路径
	 * @param nDefalultDrawableId
	 *            [in] 默认的背景资源Drawable的Id
	 * @return 背景图片
	 */
	public static Drawable getViewBg(final Context context, final String bgPicPath, final int nDefalultDrawableId) {

		if (null == context)
			return null;

		Drawable bgDrawable = null;
		if (!TextUtils.isEmpty(bgPicPath)) {
			bgDrawable = Drawable.createFromPath(bgPicPath);
		}

		if (null == bgDrawable || 0 != nDefalultDrawableId)
			bgDrawable = context.getResources().getDrawable(nDefalultDrawableId);

		return bgDrawable;
	}

	/**
	 * 获取控件的背景
	 * 
	 * @param context
	 *            [in] 设备上下文
	 * @param picStream
	 *            [in] 背景图片的数据流
	 * @param nDefalultDrawableId
	 *            [in] 默认的背景资源Drawable的Id
	 * @return 背景图片
	 */
	public static Drawable getViewBg(final Context context, final InputStream picStream, final int nDefalultDrawableId) {
		if (null == context)
			return null;

		Drawable bgDrawable = null;
		if (null != picStream) {
			bgDrawable = Drawable.createFromStream(picStream, "picStream");
		}

		if (null == bgDrawable || 0 != nDefalultDrawableId)
			bgDrawable = context.getResources().getDrawable(nDefalultDrawableId);

		return bgDrawable;
	}

	/**
	 * 获取控件的背景
	 * 
	 * @param bgPicPath
	 *            [in] 背景图片的路径
	 * @return 背景图片
	 */
	public static Drawable getViewBg(final String bgPicPath) {
		if (TextUtils.isEmpty(bgPicPath))
			return null;

		return Drawable.createFromPath(bgPicPath);
	}

	/**
	 * 获取颜色值
	 * 
	 * @param colorStr
	 *            [in] 颜色值的 string :#AARRGGBB
	 * @return 颜色值
	 */
	public static int getColor(final String colorStr) {
		if (TextUtils.isEmpty(colorStr))
			return Color.TRANSPARENT;

		return Color.parseColor(colorStr);
	}
}
