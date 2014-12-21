package com.ad.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

/**
 * 对图片进行管理的工具类。
 * 
 * @author andy.xu
 */
@SuppressLint("NewApi")
public class ImageLoader {

	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */
	private static LruCache<String, Bitmap> mMemoryCache;

	/**
	 * ImageLoader的实例。
	 */
	private static ImageLoader mImageLoader;

	@SuppressLint("NewApi")
	private ImageLoader() {
		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		// 设置图片缓存大小为程序最大可用内存的1/8
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	/**
	 * 获取ImageLoader的实例。
	 * 
	 * @return ImageLoader的实例。
	 */
	public static ImageLoader getInstance() {
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader();
		}
		return mImageLoader;
	}

	/**
	 * 将一张图片存储到LruCache中。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @param bitmap
	 *            LruCache的键，这里传入从网络上下载的Bitmap对象。
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (TextUtils.isEmpty(key) || null == bitmap)
			return;
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @return 对应传入键的Bitmap对象，或者null。
	 */
	public Bitmap getBitmapFromMemoryCache(String key) {
		if (TextUtils.isEmpty(key))
			return null;

		return mMemoryCache.get(key);
	}

	/**
	 * 
	 * @Description: 释放所有的内存内容
	 * @param
	 * @return void
	 * @throws
	 */
	public void onReleaseMemoryCache() {
		if (null != mMemoryCache)
			mMemoryCache.evictAll();
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
		// 源图片的宽度
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (width > reqWidth) {
			// 计算出实际宽度和目标宽度的比率
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName, options);
	}

	/**
	 * 
	 * @Description: 解析图片文件资源
	 * @param filePath
	 *            [in] 图片的路径
	 * @return Bitmap 图片资源
	 * @throws
	 */
	public static Bitmap decodeFile(String filePath) {

		if (TextUtils.isEmpty(filePath))
			return null;

		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		// API level > 3 才能用 BitmapFactory.Options.inPurgeable 这个变量；
		options.inPurgeable = true;
		bitmap = decodeStreamFile(filePath, options);
		return bitmap;
	}

	private static Bitmap decodeStreamFile(final String filePath, final BitmapFactory.Options opt) {

		if (TextUtils.isEmpty(filePath) || null == opt)
			return null;

		File file = new File(filePath);
		if (!file.exists())
			return null;

		InputStream is;
		try {
			is = new FileInputStream(file);

			return BitmapFactory.decodeStream(is, null, opt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError error) {
			Log.e("decodeBitmap", error.toString());
			return null;
		}
		return null;
	}
	
	public static Bitmap decodeFile(final String filePath, final int nWidth, final int nHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;// 设置图片可以被回收
		BitmapFactory.decodeFile(filePath, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, nWidth, nHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		// return BitmapFactory.decodeFile(filePath, options);
		return decodeStreamFile(filePath, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

}
