package com.appstacks.indiannaukribazaar.data;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.connection.API;
import com.appstacks.indiannaukribazaar.connection.RestAdapter;
import com.appstacks.indiannaukribazaar.connection.response.ResponseDevice;
import com.appstacks.indiannaukribazaar.connection.response.ResponseInfo;
import com.appstacks.indiannaukribazaar.model.DeviceInfo;
import com.appstacks.indiannaukribazaar.model.Info;
import com.appstacks.indiannaukribazaar.model.SortBy;
import com.appstacks.indiannaukribazaar.model.Topic;
import com.appstacks.indiannaukribazaar.model.User;
import com.appstacks.indiannaukribazaar.model.type.SortType;
import com.appstacks.indiannaukribazaar.utils.NetworkCheck;
import com.appstacks.indiannaukribazaar.utils.Tools;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThisApp extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    private static ThisApp mInstance;

    public static synchronized ThisApp get() {
        return mInstance;
    }

    private int fcm_count = 0;
    private final int FCM_MAX_COUNT = 10;
    private SharedPref shared_pref;
    private Info info = null;
    private User user = null;
    private Set<Long> last_viewed = new HashSet<>();
    private List<Topic> featured_topic = new ArrayList<>();
    private List<SortBy> sorts = new ArrayList<>();
    private FirebaseAnalytics firebaseAnalytics;

    InterstitialAd mInterstitialAd;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

        mInstance = this;
        shared_pref = new SharedPref(this);
        user = shared_pref.getUser();

        // set theme.
        Tools.refreshTheme(this);

        // Init firebase.
        FirebaseApp.initializeApp(this);

        // Init firebase ads.
        MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));
        loadInterstitialAds();

        // Obtain the Firebase Analytics.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        saveCustomLogEvent("OPEN_APP");

        obtainFirebaseToken();
        subscribeTopicNotif();

        initSortByData();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

    private void loadInterstitialAds() {
        if (!AppConfig.ADS_DETAILS_ALL || !NetworkCheck.isConnect(getApplicationContext())) return;

        // interstitial
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        if (AppConfig.ADS_DETAILS_INTERS)
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (AppConfig.ADS_DETAILS_INTERS)
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public void ShowInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            return;
        } else {
            if (mInterstitialAd != null && !mInterstitialAd.isLoading())
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    public void registerNetworkListener() {
        BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (info != null) return;
                if (NetworkCheck.isConnect(getApplicationContext())) {
                    requestCheckVersion();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private void requestCheckVersion() {
        API api = RestAdapter.createAPI();
        Call<ResponseInfo> callbackCall = api.getInfo(Tools.getVersionCode(this));
        callbackCall.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                ResponseInfo resp = response.body();
                if (resp != null && resp.status.equals("success") && resp.info != null) {
                    info = resp.info;
                    if (!resp.info.active) Tools.backToMainActivity(ThisApp.this);

                }
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    private void subscribeTopicNotif() {
        if (shared_pref.isSubscibeNotif()) return;
        FirebaseMessaging.getInstance().subscribeToTopic(AppConfig.NOTIFICATION_TOPIC).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                shared_pref.setSubscibeNotif(task.isSuccessful());
            }
        });
    }

    private void obtainFirebaseToken() {
        if (NetworkCheck.isConnect(this) && shared_pref.isNeedRegister()) {
            fcm_count++;

            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String token) {
                    String regId = token;
                    shared_pref.setFcmRegId(regId);
                    if (!TextUtils.isEmpty(regId)) sendRegistrationToServer(regId);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (fcm_count > FCM_MAX_COUNT) return;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            obtainFirebaseToken();
                        }
                    }, 500);
                }
            });

//            Task<InstanceIdResult> resultTask = FirebaseInstanceId.getInstance().getInstanceId();
//            resultTask.addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                @Override
//                public void onSuccess(InstanceIdResult instanceIdResult) {
//                    String regId = instanceIdResult.getToken();
//                    shared_pref.setFcmRegId(regId);
//                    if (!TextUtils.isEmpty(regId)) sendRegistrationToServer(regId);
//                }
//            });

//            resultTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    if (fcm_count > FCM_MAX_COUNT) return;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            obtainFirebaseToken();
//                        }
//                    }, 500);
//                }
//            });


        }
    }

    private void sendRegistrationToServer(String token) {
        Log.d("FCM_TOKEN", token + "");
        DeviceInfo deviceInfo = Tools.getDeviceInfo(this);
        deviceInfo.regid = token;

        API api = RestAdapter.createAPI();
        Call<ResponseDevice> callbackCall = api.registerDevice(deviceInfo);
        callbackCall.enqueue(new Callback<ResponseDevice>() {
            @Override
            public void onResponse(Call<ResponseDevice> call, Response<ResponseDevice> response) {
                ResponseDevice resp = response.body();
                if (resp != null && resp.status.equals("success")) {
                    shared_pref.setNeedRegister(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseDevice> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    // topic data
    public List<Topic> getFeaturedTopic() {
        return featured_topic;
    }

    public void setFeaturedTopic(List<Topic> topics) {
        this.featured_topic = topics;
    }


    public boolean isEligibleViewed(long wallpaper_id) {
        if (last_viewed.size() >= 50) last_viewed.clear();
        return last_viewed.add(wallpaper_id);
    }

    private void initSortByData() {
        sorts.add(new SortBy(SortType.DEFAULT, getString(R.string.sort_default), "id", "DESC"));
        sorts.add(new SortBy(SortType.NEW_TIME, getString(R.string.sort_time_desc), "date", "DESC"));
        sorts.add(new SortBy(SortType.OLD_TIME, getString(R.string.sort_time_asc), "date", "ASC"));
        sorts.add(new SortBy(SortType.HIGH_VIEW, getString(R.string.sort_view_desc), "total_view", "DESC"));
        sorts.add(new SortBy(SortType.LOW_VIEW, getString(R.string.sort_view_asc), "total_view", "ASC"));
    }

    public List<SortBy> getSorts() {
        return sorts;
    }

    public void resetInfo() {
        info = null;
    }

    public Info getInfo() {
        return info;
    }

    // user data

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        shared_pref.saveUser(user);
        this.user = user;
    }

    public void logout() {
        shared_pref.clearUser();
        this.user = null;
    }

    public boolean isLogin() {
        return user != null;
    }

    public void saveCustomLogEvent(String event) {
        Bundle params = new Bundle();
        event = event.replaceAll("[^A-Za-z0-9_]", "");
        params.putString("event", event);
        params.putString("device_id", Tools.getDeviceID(this));
        firebaseAnalytics.logEvent(event, params);
    }

    public void saveClassLogEvent(Class cls) {
        Bundle params = new Bundle();
        String event = cls.getSimpleName();
        event = event.replaceAll("[^A-Za-z0-9_]", "");
        params.putString("event", event);
        params.putString("device_id", Tools.getDeviceID(this));
        firebaseAnalytics.logEvent(event, params);
    }



}
