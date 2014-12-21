package com.ad.util;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

/**
 * 
 * 
 * @类名称: CameraLight
 * @描述: 控制手电筒的开关 
 * @开发者: andy.xu
 * @时间: 2014-8-19 下午4:15:23
 *
 */
public class CameraLight {
	private final static String TAG = CameraLight.class.getSimpleName();
    private Camera mCamera;
    private Camera.Parameters mParameters;
    boolean hasCamera = false;
    
    private boolean isOpened = false;
    
    private void initCamera() {
    	if (!hasCamera) {
			try {
		    	mCamera = Camera.open();
		    	mCamera.startPreview();
		    	hasCamera = true;
			} catch (RuntimeException e) {
				Log.e(TAG, "Could not open Camera"+ e);
		    	hasCamera = false;
				return;
			}
    	}
    }
    
    public void lightOff() {
    	if (hasCamera) {
    		isOpened = false;
	        mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
	        mCamera.setParameters(mParameters);
    	}
    	releaseCamera();
    }

    public void lightOn() {
    	if (hasCamera) {
    		turnOnLight();
    	} else {
    		initCamera();
    		turnOnLight();
    	}
    }
    
    private void turnOnLight() {
    	if (mParameters == null) {
			mParameters = mCamera.getParameters();
		}
        mParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(mParameters);
        isOpened = true;
    }
    
    public boolean hasCamera() {
    	return hasCamera;
    }
    
    public boolean isOpend() {
    	return isOpened;
    }
    
    public void releaseCamera() {
    	if (hasCamera) {
	        mCamera.stopPreview();
	        mCamera.release();
	        mCamera = null;
	        hasCamera = false;
    	}
    }
}
