package com.guers.umjjal.custom;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.guers.umjjal.R;
import com.guers.umjjal.WriteActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static android.app.Activity.RESULT_OK;


/**
 * Created by chae on 2017-12-05.
 */

public class CustomImageView  extends FrameLayout {
    View rootView;
    ImageView imageBox;
    ImageView imageUpload;
    ImageView imageDelete;

    FrameLayout frameLayout;
    Context mContext;
    WriteActivity writeActivity;
    int viewId;
    int requestCode;

    public CustomImageView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public void viewInit(){
        imageUpload.setVisibility(VISIBLE);
        imageDelete.setVisibility(INVISIBLE);
        imageBox.setBackground(getResources().getDrawable(R.drawable.shape_image_box));
    }

    private void init() {
        rootView = inflate(mContext, R.layout.custom_image_layout, this);
        writeActivity = (WriteActivity) mContext;
        viewId = this.getId();

        imageUpload = rootView.findViewById(R.id.image_upload);
        imageBox = rootView.findViewById(R.id.image_box);
        imageDelete = rootView.findViewById(R.id.image_delete);


        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                switch (viewId){
                    case R.id.SelectView1:
                        requestCode = WriteActivity.IMAGE_PICK_REQUEST_1;
                        break;
                    case R.id.SelectView2:
                        requestCode = WriteActivity.IMAGE_PICK_REQUEST_2;
                        break;
                    case R.id.SelectView3:
                        requestCode = WriteActivity.IMAGE_PICK_REQUEST_3;
                        break;
                }
                writeActivity.startActivityForResult(intent, requestCode);

            }
        });

        imageDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                writeActivity.customViewDeleteUpdate(viewId);
            }
        });
    }

    public void pickupImageDraw(Intent data){
        int callObj = data.getIntExtra("callObj",-1);


        if(data != null){
            Uri selectedImage = data.getData();

            imageUpload.setVisibility(INVISIBLE);
            imageDelete.setVisibility(VISIBLE);

            Glide.with(writeActivity)
                    .load(selectedImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(80,80)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageBox.setBackground(resource);
                        }
                    });
            writeActivity.customViewInsertUpdate(viewId);
        }
    }


}
