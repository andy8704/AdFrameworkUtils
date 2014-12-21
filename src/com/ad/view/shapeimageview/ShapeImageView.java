package com.ad.view.shapeimageview;

import android.content.Context;
import android.util.AttributeSet;

import com.ad.view.shapeimageview.shader.ShaderHelper;
import com.ad.view.shapeimageview.shader.SvgShader;

public class ShapeImageView extends ShaderImageView {

    public ShapeImageView(Context context) {
        super(context);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader();
    }
}
