package com.tmri.size.app;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageLoader;

/**
 * Created by jack.yan on 2017/10/30.
 */

public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default)
                .fitCenter()
                .into(imageView);
    }
}
