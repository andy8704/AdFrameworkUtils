package com.ad.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 
 * @类名称: AndyScrollView
 * @描述: 重写scrollview,可以获取滑动到底部的事件
 * @开发者: andy.xu
 * @时间: 2014-6-26 下午4:13:14
 * 
 */
public class MyScrollView extends ScrollView {
	private OnScrollToBottomListener onScrollToBottom;

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		onSet();
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		onSet();
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		onSet();
	}

	private void onSet() {
		setSmoothScrollingEnabled(true);
	}


	@SuppressLint("NewApi")
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		if (null != onScrollToBottom && clampedY && scrollY > 0) {
			onScrollToBottom.onScrollBottomListener(clampedY);
		}
	}

	public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
		onScrollToBottom = listener;
	}

	public interface OnScrollToBottomListener {
		public void onScrollBottomListener(boolean isBottom);
	}

}
