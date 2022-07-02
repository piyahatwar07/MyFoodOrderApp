package com.example.myfoodorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DesertsActivity extends AppCompatActivity {
    ImageView Back_btn;
    List<MenuList> menuList;
    String jsonUrl="https://asterisc.in/priyanka/FoodOrderApp/deserts_menu.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deserts);
        Back_btn=findViewById(R.id.back_btn);
        menuList=new ArrayList<>();
        getData();
        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DesertsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(DesertsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, jsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray MenuArray = jsonObject.getJSONArray("deserts");
                    for (int i = 0; i < MenuArray.length(); i++) {
                        JSONObject menuObject = MenuArray.getJSONObject(i);
                        MenuList menu = new MenuList(menuObject.getString("name"), menuObject.getString("price"), menuObject.getString("imageurl"));
                        menuList.add(menu);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initRecyclerView();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void initRecyclerView(){
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        DesertMenuListAdapter adapter=new DesertMenuListAdapter(menuList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}