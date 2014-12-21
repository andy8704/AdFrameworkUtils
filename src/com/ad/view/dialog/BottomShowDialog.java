package com.ad.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * 
 * @类名称: BottomShowDialog
 * @描述: 屏幕底部弹出对话框
 * @开发者: andy.xu
 * @时间: 2014-8-19 下午3:52:42
 * 
 */
public class BottomShowDialog extends Dialog {

	public BottomShowDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BottomShowDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Window dlgWindow = getWindow();
		WindowManager.LayoutParams params = dlgWindow.getAttributes();
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		if (null != displayMetrics) {
			params.width = displayMetrics.widthPixels;
		}
		dlgWindow.setAttributes(params);
		dlgWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
	}

}
