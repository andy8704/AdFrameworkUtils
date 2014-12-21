package com.ad.util;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 
 * @ClassName: AndyAnimUtil
 * @Description: 动画库
 * @author andy.xu
 * @date 2014-2-8 下午02:54:50
 * 
 */
public class AnimUtil {

	/**
	 * 
	 * @Description: X轴方向的水平移动
	 * @param fromX
	 * @param toX
	 * @param view
	 *            void
	 */
	public static void onMoveXView(final int fromX, final int toX, final View view) {
		TranslateAnimation animation = new TranslateAnimation(fromX, toX, 0, 0);
		// 添加了这行代码的作用时，view移动的时候 会有弹性效果
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(1000);
		// animation.setStartOffset(1000);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// int left = view.getLeft() + (int) (toX - fromX);
				int left = toX;
				int top = view.getTop();
				int width = view.getWidth();
				int height = view.getHeight();
				view.clearAnimation();
				view.layout(left, top, left + width, top + height);
			}
		});
		view.startAnimation(animation);
	}

	public static void onMoveXView(final int fromX, final int toX, final View view, final EasyFoneAnimationListener listener) {
		TranslateAnimation animation = new TranslateAnimation(fromX, toX, 0, 0);
		// 添加了这行代码的作用时，view移动的时候 会有弹性效果
		// animation.setInterpolator(new OvershootInterpolator());
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		animation.setDuration(200);
		// animation.setStartOffset(1000);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// int left = view.getLeft() + (int) (toX - fromX);
				int left = toX;
				int top = view.getTop();
				int width = view.getWidth();
				int height = view.getHeight();
				view.clearAnimation();
				view.layout(left, top, left + width, top + height);

				if (null != listener)
					listener.onAnimEnd();
			}
		});
		view.startAnimation(animation);
	}

	public static void onMoveYView(final int fromY, final int toY, final View view) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
		// 添加了这行代码的作用时，view移动的时候 会有弹性效果
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(600);
		// animation.setStartOffset(1000);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				int left = view.getLeft();
				int top = toY;
				int width = view.getWidth();
				int height = view.getHeight();
				view.clearAnimation();
				view.layout(left, top, left + width, top + height);
			}
		});
		view.startAnimation(animation);
	}
	
	public static void onMoveYView(final int fromY, final int toY, final View view, final EasyFoneAnimationListener listener) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
		// 添加了这行代码的作用时，view移动的时候 会有弹性效果
		animation.setInterpolator(new OvershootInterpolator(1.0f));
		animation.setDuration(600);
		// animation.setStartOffset(1000);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				int left = view.getLeft();
				int top = toY;
				int width = view.getWidth();
				int height = view.getHeight();
				view.clearAnimation();
				view.layout(left, top, left + width, top + height);
				
				if(null != listener)
					listener.onAnimEnd();
			}
		});
		view.startAnimation(animation);
	}
	
	public static void onAlphaView(final float fromAlpha, final float toAlpha, final View view, final EasyFoneAnimationListener listener){
		if(null == view)
			return;
		
		AlphaAnimation alpha  = new AlphaAnimation(fromAlpha, toAlpha);
		alpha.setDuration(200);
		alpha.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				if(null != listener)
					listener.onAnimEnd();
			}
		});
		view.startAnimation(alpha);
	}

	public static void onRorateView(final int fromDegree, final int toDegree, final View view) {

		RotateAnimation animation = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(1000);

		view.startAnimation(animation);
	}

	public static void onRorateView(final int fromDegree, final int toDegree, final View view, final EasyFoneAnimationListener listener) {

		RotateAnimation animation = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(1000);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (null != listener)
					listener.onAnimEnd();
			}
		});
		view.startAnimation(animation);
	}

	public interface EasyFoneAnimationListener {
		public void onAnimEnd();
	}
}
