package com.guers.umjjal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.guers.umjjal.common.SaveSharedPreference;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

/**
 * Created by chae on 2017-11-27.
 */

public class LoginActivity extends AppCompatActivity {

    private SessionCallback callback;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    public void loginProcess(View view) {
        switch (view.getId()) {

            case R.id.facebook_login_textview:
                Toast.makeText(getApplicationContext(), "페이스북 로그인", Toast.LENGTH_LONG).show();
                break;
            case R.id.google_login_textview:
                Toast.makeText(getApplicationContext(), "구글 로그인", Toast.LENGTH_LONG).show();
                break;
        }
    }


    //인터넷 연결상태 확인
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            //access token을 성공적으로 발급 받아 valid access token을 가지고 있는 상태. 일반적으로 로그인 후의 다음 activity로 이동한다.
            if (Session.getCurrentSession().isOpened()) { // 한 번더 세션을 체크해주었습니다.
                requestMe();
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    public void requestLogout() {
        //success_layout.setVisibility(View.GONE);
        //loginButton.setVisibility(View.VISIBLE);
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {

            }
        });
    }

    public void requestMe() {
        //success_layout.setVisibility(View.VISIBLE);
        //loginButton.setVisibility(View.GONE);
        Log.e("Call RequestMe", "호출됨??");

        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("onFailure", errorResult + "");
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("onSessionClosed", errorResult + "");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("onSuccess", userProfile.toString());

                SaveSharedPreference.setUserInfo(getApplicationContext(), SaveSharedPreference.PREF_PROVIDE_KAKAO, String.valueOf(userProfile.getId()), userProfile.getNickname(),userProfile.getProfileImagePath());

                finish();
                /*
                user_nickname.setText(userProfile.getNickname());
                user_email.setText(userProfile.getEmail());
                aQuery.id(user_img).image(userProfile.getThumbnailImagePath()); // <- 프로필 작은 이미지 , userProfile.getProfileImagePath() <- 큰 이미지*/
            }

            @Override
            public void onNotSignedUp() {
                Log.e("onNotSignedUp", "onNotSignedUp");
            }
        });

    }


}
