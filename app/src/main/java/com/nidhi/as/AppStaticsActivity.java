package com.nidhi.as;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.nidhi.as.common.AppPreferences;
import com.nidhi.as.fragments.QRCodeFragment;
import com.nidhi.as.fragments.Parent;
import com.nidhi.as.utils.TraceUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Stack;

public class AppStaticsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Context context = AppStaticsActivity.this;
    private TextView tvResult;
    private Stack<Parent> fragStack = null;

    public FragmentManager manager = null;

    public FragmentTransaction trans;

    private ImageLoader imageLoader;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_statics);

        tvResult = (TextView) findViewById(R.id.tv_result);


        ImageLoader imageLoader = ImageLoader.getInstance();

        String res = AppPreferences.getInstance(context).getStringFromStore("uname");

        tvResult.setText("welcome" + " " + res);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView) headerview.findViewById(R.id.imageView);


        /*String fimageUri = AppPreferences.getInstance(this).getStringFromStore("profpic");
        TraceUtils.logE("imageUri", fimageUri);
        imageLoader.displayImage(fimageUri, imageView);*/

        String gimageUri = AppPreferences.getInstance(this).getStringFromStore("profpic");
        TraceUtils.logE("imageUri", gimageUri);
        imageLoader.displayImage(gimageUri, imageView);


        TextView tv_name = (TextView) headerview.findViewById(R.id.tv_name);
        tv_name.setText(AppPreferences.getInstance(context).getStringFromStore("name"));
//        tv_name.setText(AppPreferences.getInstance(context).getStringFromStore("guname"));

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, null).
                addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_statics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.nav_camera:
                //load camera fragment.
                //getSupportFragmentManager().beginTransaction().replace(R.id.container,new QRCodeFragment()).commit();
                swiftFragments(QRCodeFragment.newInstance(), "home");
                break;
            case R.id.nav_gallery:
                // getSupportFragmentManager().beginTransaction().replace(R.id.container, new GalleryFragment()).commit();
                break;
            case R.id.nav_slideshow:// inject Slide show fragment.
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, new SlideshowFragment()).commit();
                break;
            case R.id.nav_manage:
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, new ToolsFragment()).commit();
                break;
            case R.id.nav_logout:

                AppPreferences.getInstance(context).clearSharedPreferences();
                LoginManager.getInstance().logOut();
                if (mGoogleApiClient.isConnected())
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                AppStaticsActivity.this.finish();
                break;



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void swiftFragments(Parent frag, String tag) {
        trans = manager.beginTransaction();
        if (frag.isAdded() && frag.isVisible()) return;
        if (frag.isAdded() && frag.isHidden()) {
            trans.hide(fragStack.get(fragStack.size() - 1));
            trans.show(frag);
        } else if (!frag.isAdded()) {
            if (fragStack.size() > 0) {
                Parent pf = fragStack.get(fragStack.size() - 1);
                trans.hide(pf);
            }
            trans.add(R.id.container, frag, tag);
            trans.show(frag);
        }
        trans.commit();
        fragStack.push(frag);
    }

    public void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
