package com.example.myfoodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    EditText edtTxtUsername, edtMobile, edtTxtEmail, edtTxtPassword;
    TextView txtLogin;
    Button btnRegister;
    ProgressBar progressBar;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressBar = findViewById(R.id.progressBar);
        edtTxtUsername = findViewById(R.id.editTextUsername);
        edtTxtEmail = findViewById(R.id.editTextEmail);
        edtTxtPassword = findViewById(R.id.editTextPassword);
        edtMobile = findViewById(R.id.editTextMobileNo);
        txtLogin = findViewById(R.id.txtLogin);
        btnRegister = findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckEditText()) {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser();
                    // startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegistrationActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });
    }

    public boolean CheckEditText() {
        final String username = edtTxtUsername.getText().toString().trim();
        final String mobile = edtMobile.getText().toString().trim();
        final String email = edtTxtEmail.getText().toString().trim();
        final String password = edtTxtPassword.getText().toString().trim();
        String regex = "(0|91)?[7-9][0-9]{9}";
        String number = edtMobile.getText().toString();
        if (TextUtils.isEmpty(username)) {
            edtTxtUsername.setError("Please enter username");
            edtTxtUsername.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mobile) || (number.matches(regex) == false)) {
            edtMobile.setError("Please enter valid mobile no.");
            edtMobile.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            edtTxtEmail.setError("Please enter your email");
            edtTxtEmail.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtTxtEmail.setError("Enter a valid email");
            edtTxtEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edtTxtPassword.setError("Enter a password");
            edtTxtPassword.requestFocus();
            return false;
        }
        return true;

    }

    private void registerUser() {
        final String username = edtTxtUsername.getText().toString().trim();
        final String mobile = edtMobile.getText().toString().trim();
        final String email = edtTxtEmail.getText().toString().trim();
        final String password = edtTxtPassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("mobile"),
                                        userJson.getString("email")
                                        // userJson.getString("password")

                                        //   userJson.getString("gender")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                // finish();
                                //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //  params.put("email",username);
                //  params.put("pass",password);
                params.put("username", username);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("password", password);
                // params.put("gender", gender);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}