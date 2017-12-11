/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guers.umjjal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class CheeseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.comm_content1_imageview);
        ImageView imageView2 = findViewById(R.id.comm_content2_imageview);
        TextView textView = findViewById(R.id.comm_content_title_textview);
        TextView textView1 = findViewById(R.id.comm_content_textview);

        TextView textView2 = findViewById(R.id.comm_author_textview);
        TextView textView3 = findViewById(R.id.comm_datetable_textveiw);
        TextView textView4 = findViewById(R.id.comm_view_count_textview);



        textView.setText("제목 들어가는 자리");

        //imageView.setImageResource(R.drawable.img2);
        //imageView2.setImageResource(R.drawable.image1);
        imageView.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.VISIBLE);


        GlideDrawableImageViewTarget glideDrawableImageViewTarget = new GlideDrawableImageViewTarget(imageView);

        Glide.with(this).load(R.drawable.img2).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .thumbnail(0.1f).into(glideDrawableImageViewTarget);


        glideDrawableImageViewTarget = new GlideDrawableImageViewTarget(imageView2);
        Glide.with(this).load(R.drawable.image1).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .thumbnail(0.1f).into(glideDrawableImageViewTarget);


        textView1.setText("내용이 들어간다!!!!!!!!!!!!");

        textView2.setText("글작성자");
        textView3.setText("2017-11-23");
        textView4.setText("50");



        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        //loadBackdrop();
    }

    private void loadBackdrop() {
        //final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        //Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


}
