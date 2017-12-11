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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.guers.umjjal.common.SaveSharedPreference;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Menu menuNav;
    private final static int ACTIVITY_MENU_CODE_LOGIN=101;
    private final static int ACTIVITY_MENU_CODE_WRITE=102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("Login status",SaveSharedPreference.getUserNickName(getApplicationContext()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        menuNav = navigationView.getMenu();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton writeButton = (FloatingActionButton) findViewById(R.id.writeButton);


        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), WriteActivity.class);
                startActivityForResult(intent,ACTIVITY_MENU_CODE_WRITE);
            }
        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        loginStatusCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(), "게시판형태");
        adapter.addFragment(new CheeseImageListFragment(), "이미지 게시판");
        adapter.addFragment(new CheeseListFragment(), "Category 3");
        adapter.addFragment(new CheeseListFragment(), "Category 4");
        adapter.addFragment(new CheeseListFragment(), "Category 5");
        adapter.addFragment(new CheeseListFragment(), "Category 6");
        adapter.addFragment(new CheeseListFragment(), "Category 7");
        adapter.addFragment(new CheeseListFragment(), "Category 8");
        adapter.addFragment(new CheeseListFragment(), "Category 9");
        adapter.addFragment(new CheeseListFragment(), "Category 10");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.nav_login:
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        startActivityForResult(intent,ACTIVITY_MENU_CODE_LOGIN);
                        break;
                    case R.id.nav_logout:
                        requestLogout();
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(),"선택 메뉴 : 공지사항",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_messages:
                        Toast.makeText(getApplicationContext(),"선택 메뉴 : 메세지",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(getApplicationContext(),"선택 메뉴 : 설정",Toast.LENGTH_LONG).show();
                        break;

                }

                //menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(ACTIVITY_MENU_CODE_LOGIN == requestCode){
            loginStatusCheck();
        }

    }

    public void loginStatusCheck(){
        View nav_view = navigationView.getHeaderView(0);

        TextView user_nick_name = nav_view.findViewById(R.id.user_nick_name);
        CircleImageView user_image = nav_view.findViewById(R.id.user_image);
        MenuItem nav_login = menuNav.findItem(R.id.nav_login);
        MenuItem nav_logout = menuNav.findItem(R.id.nav_logout);


        Log.e("user_nick_name ->>","Object ->"+user_nick_name);
        if(!TextUtils.isEmpty(SaveSharedPreference.getUserId(getApplicationContext()))){    //로그인상태

            user_nick_name.setText(SaveSharedPreference.getUserNickName(getApplicationContext()));

            if(TextUtils.isEmpty(SaveSharedPreference.getUserImagePath(getApplicationContext()))){
                user_image.setImageDrawable(getResources().getDrawable(R.drawable.img_profile_default));
            }else{
                user_image.setImageURI(Uri.parse(SaveSharedPreference.getUserImagePath(getApplicationContext())));
            }

            nav_login.setVisible(false);
            nav_logout.setVisible(true);
        }else{
            nav_login.setVisible(true);
            nav_logout.setVisible(false);
        }
    }

    public void requestLogout(){
        Log.e("logout","카카오 로그아웃");
        switch (SaveSharedPreference.getProvideTarget(getApplicationContext())){
            case SaveSharedPreference.PREF_PROVIDE_KAKAO:

                SaveSharedPreference.clearUserName(getApplicationContext());
                loginStatusCheck();
                break;

        }


    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
