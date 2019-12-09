package com.example.android.enlight_me;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.enlight_me.ui.login.LoginActivity;
import com.example.android.enlight_me.ui.register.RegisterFormState;
import com.example.android.enlight_me.ui.register.RegisterViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    String email[] = new String[50];
    String password[] = new String[50];
    String username[] = new String[50];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       final RequestQueue queue = Volley.newRequestQueue(this);


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText fullNameEditText = findViewById(R.id.fullName);
        final EditText emailEditText = findViewById(R.id.email);
        final Button registerButton = findViewById(R.id.register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);





        RegisterViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {

                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                RegisterViewModel.RegisterDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),emailEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);


        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
//               int flag=0;
//                for(int i=0;i<3;i++){
//
//
//                    if(usernameEditText.getText().toString().equals(username[i])||passwordEditText.getText().toString().equals(password[i])||emailEditText.getText().toString().equals(email[i])){
//                      flag=1;
//                    }
//                }
//                if(flag==1){
//                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
//
//                }
//                else{
//
//                    Toast.makeText(getApplicationContext(), "created successfully", Toast.LENGTH_SHORT).show();
//
//                }

                final JSONObject jsonBody = new JSONObject();
                String url = "http://10.0.2.2:3000/users/user";

                try {
                    jsonBody.put("email", emailEditText.getText().toString());
                    jsonBody.put("username", usernameEditText.getText().toString());
                    jsonBody.put("password", passwordEditText.getText().toString());
                    jsonBody.put("name", fullNameEditText.getText().toString());

                    JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                  Toast.makeText(getApplicationContext(), "created successfully", Toast.LENGTH_LONG).show();
                            Intent mainIntent=new Intent(Register.this, LoginActivity.class);
                            startActivity(mainIntent);
                            finish();


               //             Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                       //     onBackPressed();
                            System.out.println(error);
                            System.out.println("error occured");
                  Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();


                        }
                    });
                           queue.add(jsonOblect);
                           System.out.println(jsonOblect);



                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("ERRRROOOOOOORR");
                }
                final String requestBody = jsonBody.toString();
                System.out.println(requestBody);



//                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>()
//                        {
//                            @Override
//                            public void onResponse(String response) {
//                                // response
//                                Log.d("Response", response);
//                                Toast.makeText(getApplicationContext(), "created successfully", Toast.LENGTH_SHORT).show();
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // error
//                                Log.d("Error.Response", error.toString());
//                                System.out.println("failed");
//                                Toast.makeText(getApplicationContext(), "creation failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                )
//                {
//                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
//                        Map<String,String> params = new HashMap<String, String>();
//                        params.put("email", emailEditText.getText().toString());
//                        params.put("username", usernameEditText.getText().toString());
//                        params.put("password", passwordEditText.getText().toString());
//                        params.put("name", fullNameEditText.getText().toString());
//                        System.out.println(params);
//                        return params;
//                    };
//                };
//            //    System.out.println(postRequest);
//                postRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



        //        queue.add(postRequest);

            }
        });
    }

            }






