package com.guers.umjjal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.guers.umjjal.custom.CustomImageView;
import com.kakao.auth.Session;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by chae on 2017-12-05.
 */

public class WriteActivity extends AppCompatActivity {
    private final static int LOAD_IMAGE_RESULT = 101;
    public final static int  IMAGE_PICK_REQUEST_1 = 111;
    public final static int  IMAGE_PICK_REQUEST_2 = 112;
    public final static int  IMAGE_PICK_REQUEST_3 = 113;

    CustomImageView selectView1;
    CustomImageView selectView2;
    CustomImageView selectView3;

    ImageView imageUpload;
    Drawable.ConstantState defaultCS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        selectView1 = findViewById(R.id.SelectView1);
        selectView2 = findViewById(R.id.SelectView2);
        selectView3 = findViewById(R.id.SelectView3);

    }
    public void pickupShowGallay(){

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case IMAGE_PICK_REQUEST_1:
                selectView1.pickupImageDraw(data);
                break;
            case IMAGE_PICK_REQUEST_2:
                selectView2.pickupImageDraw(data);
                break;
            case IMAGE_PICK_REQUEST_3:
                selectView3.pickupImageDraw(data);
                break;
        }
    }

    public void customViewInsertUpdate(int viewId){

        switch (viewId) {
            case R.id.SelectView1:
                selectView2.setVisibility(View.VISIBLE);
                break;
            case R.id.SelectView2:
                selectView3.setVisibility(View.VISIBLE);
                break;
            case R.id.SelectView3:
                break;
        }

    }

    public void customViewDeleteUpdate(int viewId){
        defaultCS= getResources().getDrawable(R.drawable.shape_image_box).getConstantState();
        Drawable.ConstantState selectView1_CS = selectView1.findViewById(R.id.image_box).getBackground().getConstantState();
        Drawable.ConstantState selectView2_CS = selectView2.findViewById(R.id.image_box).getBackground().getConstantState();
        Drawable.ConstantState selectView3_CS = selectView3.findViewById(R.id.image_box).getBackground().getConstantState();



        switch (viewId){
            case R.id.SelectView1:
                copyImageView(selectView1,selectView2);
                copyImageView(selectView2,selectView3);

                //ImageView 입력이 두개라면 하나는 visible 처리
                if(selectView2_CS.equals(defaultCS) && selectView3_CS.equals(defaultCS)){
                    selectView2.setVisibility(View.INVISIBLE);
                    selectView3.setVisibility(View.INVISIBLE);
                }else if(!selectView2_CS.equals(defaultCS) && selectView3_CS.equals(defaultCS)){
                    selectView3.setVisibility(View.INVISIBLE);
                }else{
                    selectView3.viewInit();
                }

                break;
            case R.id.SelectView2:
                copyImageView(selectView2,selectView3);

                if(selectView3_CS.equals(defaultCS)){
                    selectView3.setVisibility(View.INVISIBLE);
                }else{
                    selectView3.viewInit();
                }

                break;
            case R.id.SelectView3:
                selectView3.viewInit();
                break;
        }
    }

    protected  void copyImageView(CustomImageView target, CustomImageView source ){
        target.findViewById(R.id.image_box).setBackground(source.findViewById(R.id.image_box).getBackground());

        if(target.findViewById(R.id.image_box).getBackground().getConstantState().equals(defaultCS)){
            target.viewInit();
        }
    }

}


