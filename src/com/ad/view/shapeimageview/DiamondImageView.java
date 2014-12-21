package com.ad.view.shapeimageview;

import android.content.Context;
import android.util.AttributeSet;

import com.ad.view.shapeimageview.shader.ShaderHelper;
import com.ad.view.shapeimageview.shader.SvgShader;
import com.example.adframeworkutil.R;

public class DiamondImageView extends ShaderImageView {

    public DiamondImageView(Context context) {
        super(context);
    }

    public DiamondImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiamondImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.imgview_diamond);
    }
}
