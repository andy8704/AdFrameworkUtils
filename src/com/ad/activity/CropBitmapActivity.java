package com.ad.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.ad.bitmap.BitmapDecodeUtil;
import com.ad.cropper.CropImageView;
import com.example.adframeworkutil.R;

/**
 * 
 * 
 * @类名称: ForumCropBmpActivity
 * @描述: 图片裁剪 (输入intent参数是"path", 返回的也是"path")
 * @开发者: andy.xu
 * @时间: 2014-8-15 下午12:01:58
 * 
 */
public class CropBitmapActivity extends Activity {

	private ImageView mBackBtn = null, mOkBtn = null;
	private CropImageView mCropImgView = null;
	private String bmpPath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		bmpPath = getIntent().getStringExtra("path");
		setContentView(R.layout.crop_bmp_activity);

		initUI();
	}

	private void initUI() {

		mBackBtn = (ImageView) findViewById(R.id.back_btn_id);
		mOkBtn = (ImageView) findViewById(R.id.ok_btn_id);

		mCropImgView = (CropImageView) findViewById(R.id.CropImageView);

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		mOkBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onSave();
			}
		});

		DisplayMetrics dis = getResources().getDisplayMetrics();
		Bitmap bmp = BitmapDecodeUtil.decodeFile(bmpPath, dis.widthPixels, dis.heightPixels);
		if (null != bmp)
			mCropImgView.setImageBitmap(bmp);
	}

	private void onSave() {
		Bitmap bmp = mCropImgView.getCroppedImage();
		if (null != bmp) {
			String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "tempSave.png";
			BitmapDecodeUtil.onSaveBmpToFile(bmp, savePath, 100);

			Intent intent = new Intent();
			intent.putExtra("path", savePath);
			setResult(RESULT_OK, intent);
			finish();
		}
	}

}
