package com.appstacks.indiannaukribazaar.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.AaForChecking;
import com.appstacks.indiannaukribazaar.NewActivities.EditProfileActivity;
import com.appstacks.indiannaukribazaar.NewActivities.FindJobsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs.KycStartBrowsingActivity;
import com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs.WelldoneActivity;
import com.appstacks.indiannaukribazaar.NewActivities.SpinWheelActivity;


import com.appstacks.indiannaukribazaar.NewFragments.ProfileFragment;
import com.appstacks.indiannaukribazaar.NewFragments.WellDoneFragment;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.Slider.SliderAdapter;
import com.appstacks.indiannaukribazaar.Slider.SliderData;
import com.appstacks.indiannaukribazaar.databinding.ActivityMainBinding;
import com.appstacks.indiannaukribazaar.model.DeviceInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.data.AppConfig;
import com.appstacks.indiannaukribazaar.data.Constant;
import com.appstacks.indiannaukribazaar.data.GDPR;
import com.appstacks.indiannaukribazaar.data.ThisApp;
import com.appstacks.indiannaukribazaar.fragment.FragmentHome;
import com.appstacks.indiannaukribazaar.fragment.FragmentSaved;
import com.appstacks.indiannaukribazaar.fragment.FragmentTopic;
import com.appstacks.indiannaukribazaar.model.Info;
import com.google.android.gms.ads.InterstitialAd;
import com.appstacks.indiannaukribazaar.model.User;
import com.appstacks.indiannaukribazaar.room.AppDatabase;
import com.appstacks.indiannaukribazaar.room.DAO;
import com.appstacks.indiannaukribazaar.utils.NetworkCheck;
import com.appstacks.indiannaukribazaar.utils.Tools;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.PageIndicatorView;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    ActivityMainBinding binding;

    private DatabaseReference allUserRef, deviceRefmain;
    private FirebaseAuth auth;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private View notif_badge = null, notif_badge_menu = null;
    private int notification_count = -1;
    BottomNavigationView bottomNav;

    private FragmentHome fragmentHome;
    private FragmentTopic fragmentTopic;
    private FragmentSaved fragmentSaved;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private boolean dialog_version_show = false;
    private boolean is_login = false;
    private User user = new User();
    private DAO dao;
    ThisApp applicationHelper;
    private List items = new ArrayList<>();
    private SliderAdapter adapter;
    private ArrayList<SliderData> sliderDataArrayList;
    FirebaseFirestore db;
    private SliderView sliderView;
    private PageIndicatorView pageindi;

    String currentUserAuth;
    DeviceInfo deviceInfo;

    LinearLayout searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
        }
        auth = FirebaseAuth.getInstance();
        currentUserAuth = auth.getCurrentUser().getUid();
        allUserRef = FirebaseDatabase.getInstance().getReference("AllUsers").child(currentUserAuth);
        deviceRefmain = FirebaseDatabase.getInstance().getReference();

        deviceInfo = Tools.getDeviceInfo(this);

        deviceDisable();

//        bottomNav = findViewById(R.id.bottomNav);

        // creating a new array list fr our array list.
        sliderDataArrayList = new ArrayList<>();

        // initializing our slider view and
        // firebase firestore instance.
        sliderView = findViewById(R.id.slider);
        pageindi = findViewById(R.id.pageindi);

        db = FirebaseFirestore.getInstance();

        // calling our method to load images.


//        loadImages();

        LinearLayout floating = findViewById(R.id.floatingClick);

        floating.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AaForChecking.class));
        });

        dao = AppDatabase.getDb(this).getDAO();
        ThisApp.get().registerNetworkListener();
//        loadInterstitialAds();
        initToolbar();
//        initDrawerMenu();
        prepareAds();

        // load first fragment
//        actionBar.setTitle(getString(R.string.title_menu_home));
        loadFragment(new FragmentHome());
        bottomNavigations();

        checkAppVersion();
        Tools.RTLMode(getWindow());
        ThisApp.get().saveClassLogEvent(getClass());

    }

    public void deviceDisable() {
        deviceRefmain.child("RegDevices").child(deviceInfo.device_id)
                .child("valid")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Boolean istrue = snapshot.getValue(boolean.class);
//                            sendOnChannel1();
                            if (istrue) {


                            } else {
                                FirebaseAuth.getInstance().signOut();
//                                Toast.makeText(ActivityMain.this, " Is signOut", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityMain.this, ActivitySplash.class);
                                startActivity(intent);
                                finishAffinity();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    public void bottomNavigations() {
        boolean ch = false;

        BottomNavigationView navigation;
        navigation = findViewById(R.id.bottomNavigationnn);

//        Menu a = navigation.getMenu();
//        MenuItem b = a.findItem(R.id.profile);
//        if(ch){
//            b.setIcon(R.drawable.userimg);
//        }
//        else{
//            b.setIcon(R.drawable.ic_profile_svg);
//        }

        binding.bottomNavigationnn.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.homeMain:
                    loadFragment(new FragmentHome());
                    break;
                case R.id.invest:
                    Toast.makeText(getApplicationContext(), "coming soon", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.profile:
                    startActivity(new Intent(ActivityMain.this, EditProfileActivity.class));

                    break;

                case R.id.find_job:
//                    loadFragment(new JobButtonsFragment());
                    binding.progressBar4.setVisibility(View.VISIBLE);
                    allUserRef.child("verification").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.progressBar4.setVisibility(View.GONE);
                                boolean istrue = snapshot.getValue(Boolean.class);
                                if (istrue) {
                                    startActivity(new Intent(ActivityMain.this, FindJobsActivity.class));

                                } else {
//                                    Intent intent = new Intent(ActivityMain.this, WelldoneActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                    startActivity(intent);
//                                    startActivity(new Intent(getApplicationContext(), WelldoneActivity.class));
                                    loadFragment(new WellDoneFragment());

                                }

                            } else {
                                Intent intent = new Intent(ActivityMain.this, KycStartBrowsingActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                binding.progressBar4.setVisibility(View.GONE);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

//                    Intent intent = new Intent(ActivityMain.this,KycStartBrowsingActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(intent);

//                   startActivity(new Intent(ActivityMain.this, KycStartBrowsingActivity.class));






            }
            return true;
        });


    }


    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
//        searchBox = findViewById(R.id.searchclick_Id);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorTextAction), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        ImageView im = findViewById(R.id.imageViewlogo);
        im.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), currentUserAuth, Toast.LENGTH_SHORT).show();
        });

        //Search for mainActivity

//        searchBox.setOnClickListener(view -> {
//            ActivitySearch.navigate(this, null, null);
////            Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
//        });
//        searchBox.setEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.changeOverflowMenuIconColor(toolbar, getResources().getColor(R.color.colorTextAction));
        Tools.setSmartSystemBar(this);
    }

    public void ShowInterstitial() {
        applicationHelper = (ThisApp) getApplicationContext();
        applicationHelper.ShowInterstitial();
     /*   if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
            return;
        } else {
            if (mInterstitialAd != null && !mInterstitialAd.isLoading())
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }*/

    }


    private void loadInterstitialAds() {
//        if (!AppConfig.ADS_DETAILS_ALL || !NetworkCheck.isConnect(getApplicationContext())) return;

        // interstitial
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
//        if (AppConfig.ADS_DETAILS_INTERS)
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

    private void initDrawerMenu() {
        NavigationView nav_view = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        TextView name = nav_view.findViewById(R.id.name);
        TextView login_logout = nav_view.findViewById(R.id.login_logout);
        TextView settings = nav_view.findViewById(R.id.settings);
        ImageView avatar = nav_view.findViewById(R.id.avatar);
        notif_badge_menu = nav_view.findViewById(R.id.notif_badge_menu);
        if (is_login) {
            login_logout.setText(getString(R.string.logout_title));
            name.setText(user.name);
            Tools.displayImageCircle(this, avatar, Constant.getURLimgUser(user.image));
        } else {
            login_logout.setText(getString(R.string.title_activity_login));
            avatar.setImageDrawable(null);
            name.setText("");
        }

        login_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLogout();
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_login) {
                    ActivityRegisterProfile.navigate(ActivityMain.this, user);
                } else {
                    ActivityLogin.navigate(ActivityMain.this);
                }
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_login) {
                    ActivityRegisterProfile.navigate(ActivityMain.this, user);
                } else {
                    ActivityLogin.navigate(ActivityMain.this);
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySettings.navigate(ActivityMain.this);
            }
        });

        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        });
    }

    public void onDrawerMenuClick(View view) {
        Fragment fragment = null;
        String title = actionBar.getTitle().toString();
        int menu_id = view.getId();
        switch (menu_id) {
            case R.id.nav_menu_home:
                if (fragmentHome == null) fragmentHome = new FragmentHome();
                fragment = fragmentHome;
                title = getString(R.string.title_menu_home);
                break;
            case R.id.nav_menu_topic:
                if (fragmentTopic == null) fragmentTopic = new FragmentTopic();
                fragment = fragmentTopic;
                title = getString(R.string.title_menu_topic);
                break;
            case R.id.nav_menu_notif:
                ActivityNotification.navigate(this);
                break;
            case R.id.nav_menu_saved:
                if (fragmentSaved == null) fragmentSaved = new FragmentSaved();
                fragment = fragmentSaved;
                title = getString(R.string.title_menu_saved);
                break;
            case R.id.nav_menu_rate:
                Tools.rateAction(this);
                break;
            case R.id.nav_menu_disclaimer:
                String url = "https://sites.google.com/view/indiannokribazaar/home";
                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(url));
                startActivity(i1);
                break;


            case R.id.dmca:
                String url1 = "https://sites.google.com/view/indiannaukribazaar/home";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url1));
                startActivity(i);
                break;


        }
        actionBar.setTitle(title);
        drawer.closeDrawers();
        if (fragment != null) loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.colorTextAction));

        final MenuItem menuItem = menu.findItem(R.id.action_notification);
        View actionView = MenuItemCompat.getActionView(menuItem);
        notif_badge = actionView.findViewById(R.id.notif_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();

        if (menu_id == android.R.id.home) {
            if (!drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.openDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
        }
//        else if (menu_id == R.id.action_search) {
//            ActivitySearch.navigate(this, null, null);
//        }
        else if (menu_id == R.id.action_notification) {
            ActivityNotification.navigate(this);
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);


    }

    private void setupBadge() {
        if (notif_badge == null) return;
        notif_badge.setVisibility(notification_count == 0 ? View.INVISIBLE : View.VISIBLE);
        notif_badge_menu.setVisibility(notification_count == 0 ? View.INVISIBLE : View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (!drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.openDrawer(GravityCompat.START);
//        } else {
//            doExitApp();
//        }

        doExitApp();

    }

    static boolean active = false;

    @Override
    protected void onResume() {
        super.onResume();
        is_login = ThisApp.get().isLogin();
        user = ThisApp.get().getUser();
        initDrawerMenu();
        int new_notif_count = dao.getNotificationUnreadCount();
        if (new_notif_count != notification_count) {
            notification_count = new_notif_count;
            invalidateOptionsMenu();
        }
        if (mAdView != null) mAdView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdView != null) mAdView.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }

    public void loginLogout() {
        if (is_login) {
            showDialogLogout();
        } else {
            ActivityLogin.navigate(this);
        }
    }

    private void showDialogLogout() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.confirmation);
        dialog.setMessage(R.string.logout_confirmation_text);
        dialog.setNegativeButton(R.string.CANCEL, null);
        dialog.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ThisApp.get().logout();
                onResume();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void checkAppVersion() {
        if (dialog_version_show) return;
        Info info = ThisApp.get().getInfo();
        if (info != null && !info.active) {
            dialogOutDate();
        }
    }

    public void dialogOutDate() {
        dialog_version_show = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_info);
        builder.setMessage(R.string.msg_app_out_date);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.UPDATE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog_version_show = false;
                dialog.dismiss();
                Tools.rateAction(ActivityMain.this);
                finish();
            }
        });
        builder.setNegativeButton(R.string.CLOSE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog_version_show = false;
                dialog.dismiss();
                Tools.closeApplication(ActivityMain.this);
            }
        });
        builder.show();
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private void prepareAds() {


        if (AppConfig.ENABLE_GDPR) GDPR.updateConsentStatus(this); // init GDPR
        if (!AppConfig.ADS_MAIN_ALL || !NetworkCheck.isConnect(getApplicationContext())) return;

        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .build();

        mAdView.loadAd(adRequest);
    }

    private void loadImages() {
        // Code in Line 106
        // getting data from our collection and after
        // that calling a method for on success listener.
        db.collection("Slider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                // inside the on success method we are running a for loop
                // and we are getting the data from Firebase Firestore
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    // after we get the data we are passing inside our object class.
                    SliderData sliderData = documentSnapshot.toObject(SliderData.class);
                    SliderData model = new SliderData();

                    // below line is use for setting our
                    // image url for our modal class.
                    model.setimageLink(sliderData.getimageLink());
                    model.setWebsiteLink(sliderData.getWebsiteLink());
                    model.setText(sliderData.getText());

                    // after that we are adding that
                    // data inside our array list.
                    sliderDataArrayList.add(model);

                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                    adapter = new SliderAdapter(ActivityMain.this, sliderDataArrayList);

                    // belows line is for setting adapter
                    // to our slider view
                    /*Slider Adapter*/
//                    sliderView.setSliderAdapter(adapter);

                    // below line is for setting animation to our slider.
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                    // below line is for setting auto cycle duration.
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

                    // below line is for setting
                    // scroll time animation
                    sliderView.setScrollTimeInSec(3);


                    // below line is for setting auto
                    // cycle animation to our slider
                    sliderView.setAutoCycle(true);


                    sliderView.setPageIndicatorView(pageindi);

                    // below line is use to start
                    // the animation of our slider view.
                    sliderView.startAutoCycle();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we get any error from Firebase we are
                // displaying a toast message for failure
                Toast.makeText(ActivityMain.this, "Fail to load slider data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toastString(String text) {
        Toast.makeText(ActivityMain.this, text, Toast.LENGTH_SHORT).show();
    }

    // done
}