package com.example.myfoodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle mDrawerToggle;
    TextView u_name, u_phone;
    DrawerLayout mDrawer;
    NavigationView nv;
    ImageView Toggle_btn;
    RelativeLayout Biryani_menu, Pizza_menu, Desert_menu, Burger_menu;
    private Timer timer;
    private int current_posotion = 0;
    String jsonUrl = "https://asterisc.in/priyanka/FoodOrderApp/advertisement.json";
    String Lat, Lag;
    Double lat, lag;
    ViewPager viewPager, viewPager1;
    LinearLayout sliderDotspanel, sliderDotspane2;
    private int dotscount;
    private ImageView[] dots;
    boolean doubleBackToExitPressedOnce = false;

    TextView Address;
    RequestQueue rq;
    List<AdvertismentModel> sliderImg;
    AdvertismentPagerAdapter viewPagerAdapter;

     String Advertisment_url = "https://api.jsonbin.io/b/5efdbe1cbb5fbb1d2562bba7/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nv = findViewById(R.id.nv);
        Toggle_btn = findViewById(R.id.Toggle_btn);
        mDrawer = findViewById(R.id.drawer_layout);
        Biryani_menu = findViewById(R.id.Biryani_relative);
        Pizza_menu = findViewById(R.id.Pizza_relative);
        Desert_menu = findViewById(R.id.Deserts_relative);
        Burger_menu = findViewById(R.id.Burger_relative);
        rq = LoadAdvertisment.getInstance(this).getRequestQueue();
        sliderImg = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        Advertisment();
        createSlide();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                //   dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Biryani_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BiryaniActivity.class);
                startActivity(i);
                finish();
            }
        });
        Pizza_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PizzaActivity.class);
                startActivity(i);
                finish();
            }
        });
        Desert_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DesertsActivity.class);
                startActivity(i);
                finish();
            }
        });
        Burger_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BurgerActivity.class);
                startActivity(i);
                finish();
            }
        });
        Toggle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }
        };
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Order_History:
                        Intent i8 = new Intent(MainActivity.this, OrderActivity.class);
                        startActivity(i8);
                        break;
                    case R.id.About:
                        Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                        //  Intent intent4 = new Intent(After_Customer_Login_2.this, About_us.class);
                        //  startActivity(intent4);
                        break;
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "LogOut", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor e = sp.edit();
                        e.clear();
                        e.commit();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });
        View headView = nv.getHeaderView(0);
        User user = SharedPrefManager.getInstance(this).getUser();
        u_name = headView.findViewById(R.id.NavigationCustomerName);
        u_phone = headView.findViewById(R.id.NavigationCustomerPhone);
        u_name.setText(user.getName());
        u_phone.setText(user.getPhone());

    }
   public void Advertisment(){

      JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, jsonUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.e("", "" + response);

                for(int i = 0; i < response.length(); i++){

                    AdvertismentModel sliderUtils = new AdvertismentModel();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        sliderUtils.setSliderImageUrl(jsonObject.getString("images"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sliderImg.add(sliderUtils);

                }

                viewPagerAdapter = new AdvertismentPagerAdapter(sliderImg, MainActivity.this);
                viewPager.setAdapter(viewPagerAdapter);
                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(MainActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 0, 8, 0);
                    sliderDotspanel.addView(dots[i], params);
                    //7 sliderDotspane2.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        LoadAdvertisment.getInstance(MainActivity.this).addToRequestQueue(jsonArrayRequest);

    }

    private void createSlide(){

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_posotion== sliderImg.size())

                    current_posotion = 0;
                viewPager.setCurrentItem(current_posotion++, true);


            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);
    }



}
