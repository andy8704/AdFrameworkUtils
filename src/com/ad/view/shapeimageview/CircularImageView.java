package com.ad.view.shapeimageview;

import android.content.Context;
import android.util.AttributeSet;

import com.ad.view.shapeimageview.shader.CircleShader;
import com.ad.view.shapeimageview.shader.ShaderHelper;

public class CircularImageView extends ShaderImageView {

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new CircleShader();
    }
}
