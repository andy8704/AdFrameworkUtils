package com.ad.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CoverFlow extends Gallery {   
       
    private Camera mCamera = new Camera();   
    private int mMaxRotationAngle = 60;   
    private int mMaxZoom = -400;   
    private int mCoveflowCenter;   
    private boolean mAlphaMode = true;   
    private boolean mCircleMode = false;
    private boolean mScrollLeft = false;
    private boolean mScrollRight = false;
    public CoverFlow(Context context) {   
        super(context);   
        this.setStaticTransformationsEnabled(true);   
    }   
    public CoverFlow(Context context, AttributeSet attrs) {   
        super(context, attrs);   
        this.setStaticTransformationsEnabled(true);   
    }   
    public CoverFlow(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);    
        this.setStaticTransformationsEnabled(true);   
    }   
    public int getMaxRotationAngle() {   
        return mMaxRotationAngle;   
    }   
    public void setMaxRotationAngle(int maxRotationAngle) {   
        mMaxRotationAngle = maxRotationAngle;   
    }   
    public boolean getCircleMode() {   
        return mCircleMode;   
    }   
    public void setCircleMode(boolean isCircle) {   
        mCircleMode = isCircle;   
    }   
    public boolean getAlphaMode() {   
        return mAlphaMode;   
    }   
    public void setAlphaMode(boolean isAlpha) {   
        mAlphaMode = isAlpha;   
    }   
    public int getMaxZoom() {   
        return mMaxZoom;   
    }   
    public void setMaxZoom(int maxZoom) {   
        mMaxZoom = maxZoom;   
    }   
    private int getCenterOfCoverflow() {   
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2  
                + getPaddingLeft();   
    }   
    private static int getCenterOfView(View view) {   
        return view.getLeft() + view.getWidth() / 2;   
    }   
    protected boolean getChildStaticTransformation(View child, Transformation t) {   
        final int childCenter = getCenterOfView(child);   
        final int childWidth = child.getWidth();   
        int rotationAngle = 0;   
        t.clear();   
        t.setTransformationType(Transformation.TYPE_MATRIX);   
        if (childCenter == mCoveflowCenter) {   
            transformImageBitmap((ImageView) child, t, 0,0);   
        } else {   
            rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);   
            //Log.d("test", "recanglenum:"+Math.floor ((mCoveflowCenter - childCenter) / childWidth));
            if (Math.abs(rotationAngle) > mMaxRotationAngle) {   
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle   
                        : mMaxRotationAngle;   
            }   
            transformImageBitmap((ImageView) child, t, rotationAngle,(int)Math.floor ((mCoveflowCenter - childCenter) / childWidth));   
        }   
        return true;   
    }   
    /**  
     * This is called during layout when the size of this view has changed. If  
     * you were just added to the view hierarchy, you're called with the old  
     * values of 0.  
     *   
     * @param w  
     *            Current width of this view.  
     * @param h  
     *            Current height of this view.  
     * @param oldw  
     *            Old width of this view.  
     * @param oldh  
     *            Old height of this view.  
     */  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {   
        mCoveflowCenter = getCenterOfCoverflow();   
        super.onSizeChanged(w, h, oldw, oldh);   
    }   
    /**  
     * Transform the Image Bitmap by the Angle passed  
     *   
     * @param imageView  
     *            ImageView the ImageView whose bitmap we want to rotate  
     * @param t  
     *            transformation  
     * @param rotationAngle  
     *            the Angle by which to rotate the Bitmap  
     */  
    private void transformImageBitmap(ImageView child, Transformation t,   
            int rotationAngle,int d) {   
        mCamera.save();   
        final Matrix imageMatrix = t.getMatrix();   
        final int imageHeight = child.getLayoutParams().height;   
        final int imageWidth = child.getLayoutParams().width;   
        final int rotation = Math.abs(rotationAngle);   
        mCamera.translate(0.0f, 0.0f, 100.0f);   
        // As the angle of the view gets less, zoom in   
        if (rotation <= mMaxRotationAngle) {   
            float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));   
            mCamera.translate(0.0f, 0.0f, zoomAmount);   
            if (mCircleMode) {   
                if (rotation < 40)   
                    mCamera.translate(0.0f, 155, 0.0f);   
                else  
                    mCamera.translate(0.0f, (255 - rotation * 2.5f), 0.0f);   
            }   
            if (mAlphaMode) {   
                ((ImageView) (child)).setAlpha((int) (255 - rotation * 2.5));   
            }                                     
        }   
        mCamera.rotateY(rotationAngle);   
        mCamera.getMatrix(imageMatrix);   

        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));   
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));   
        mCamera.restore();   
    } 
    
    
    /**
     * check if scrolling left or right
     */
    @SuppressWarnings("unused")
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
       return e2.getX() > e1.getX();
    }

    public void scrollLeft() {
        mScrollLeft = true;
        mScrollRight = false;
     }
    public void scrollRight() {
        mScrollLeft = false;
        mScrollRight = true;
    }
    // @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
          float velocityY) {
       if (e1 == null && e2 == null) {
          super.onFling(e1, e2, velocityX, velocityY);
       } else {
          int kEvent;
          if (mScrollLeft) {
             kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
          } else if(mScrollRight){
             kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
          } else {
              kEvent = NO_ID;
          }
          onKeyDown(kEvent, null);

       }
       return true;
    }
}  