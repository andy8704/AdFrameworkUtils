package com.ad.bitmap;

import java.io.Serializable;

/**
 * 
 * 
 * @类名称: BmpCutParam
 * @描述: 图片裁剪的基本参数
 * @开发者: andy.xu
 * @时间: 2014-8-19 下午2:00:44
 * 
 */
public class BmpCutParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * startactivityForResult 的返回的resultCode
	 */
	public final static int RESULT_CUT = 0X1111;

	/**
	 * 指定剪裁的比例，若不设定，则可以任意宽度和高度
	 */
	public int aspectX = 0;

	/**
	 * 指定剪裁的比例，若不设定，则可以任意宽度和高度
	 */
	public int aspectY = 0;

	/**
	 * 是否进行裁剪比例设置
	 */
	public boolean aspectFlag = false;

	/**
	 * 设置输出的图片的宽度和高度，注意会强行设置成该大小
	 */
	public int outputX = 0;

	/**
	 * 设置输出的图片的宽度和高度，注意会强行设置成该大小
	 */
	public int outputY = 0;

	/**
	 * 是否进行图片输出的比例设置
	 */
	public boolean outputFlag = false;

	/**
	 * 输出的裁剪的图片的路径
	 */
	public String outputPath;
	
	
	public BmpCutParam() {
		outputPath = null;
	}

}
