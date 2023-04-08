package com.xf.psychology.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.xf.psychology.R;

import java.io.File;

public class GlideUtil {
    public static void load(ImageView view, String path) {
        Glide.with(view).load(path).error(R.drawable.icon_head).into(view);
    } public static void load(ImageView view, int path) {
        Glide.with(view).load(path).error(R.drawable.icon_head).into(view);
    }

    public static void loadLargeImage(Context context, int res, SubsamplingScaleImageView imageView) {
        imageView.setQuickScaleEnabled(true);
        imageView.setMaxScale(15F);
        imageView.setZoomEnabled(false);
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);

        Glide.with(context).load(res).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(resource.getAbsolutePath(), options);
                int sWidth = options.outWidth;
                int sHeight = options.outHeight;
                options.inJustDecodeBounds = false;
                WindowManager wm = ContextCompat.getSystemService(context, WindowManager.class);
                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                if (sHeight >= height && sHeight / sWidth >= 3) {
                    imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                    imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.5f, new PointF(0f, 0f), 0));
                } else {
                    imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                    imageView.setImage(ImageSource.uri(Uri.fromFile(resource)));
                    imageView.setDoubleTapZoomStyle(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER_IMMEDIATE);
                }
            }
        });

    }
}
