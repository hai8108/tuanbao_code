package com.haitian.dc;

import android.content.Context;
import android.widget.ImageView;

import com.haitian.main.R;
import com.haitian.pivot.BaseDC;
import com.haitian.pivot.BaseManager;

public class InitDC extends BaseDC {
    private ImageView imageView;
    public InitDC(Context context, BaseManager manager) {
        super(context, manager);
        imageView=new ImageView(context);
        imageView.setImageResource(R.drawable.splash_bg);
        addView(imageView);
    }
}
