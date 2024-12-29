package com.hd.muzik.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hd.muzik.R;

public class ImageLoader {
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.sync_48dp_5f6368)
                .error("https://res.cloudinary.com/dmfr3gngl/image/upload/v1734378296/avt_default_xweodu.png")
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }
}
