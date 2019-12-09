package com.example.android.enlight_me.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.enlight_me.MainActivity;
import com.example.android.enlight_me.R;
import com.example.android.enlight_me.Register;
import com.example.android.enlight_me.ui.login.LoginViewModel;
import com.example.android.enlight_me.ui.login.LoginViewModelFactory;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    String email[] = new String[50];
    String password[] = new String[50];
    String username[] = new String[50];
    int index=9999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:3000/users/all";



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsobj=new JSONObject(response);
                            JSONArray jsonArray = jsobj.getJSONArray("data");

                            for (int i = 0; jsonArray.getJSONObject(i)!=null; i++) {

                                JSONObject dataobj = jsonArray.getJSONObject(i);
                                email[i] = dataobj.getString("email");
                                password[i] = dataobj.getString("password");
                                username[i] = dataobj.getString("username");

                                System.out.println("Request completed");

                                System.out.println("email: "+email[i]+"  password: "+password[i]+"  username: "+username[i]);



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }





                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("An error occuuuuuuuuuuuuuuuuuuuuuuuurrrrrrrreeeeeeeedddd");

            }
        });




// Add the request to the RequestQueue.
        queue.add(stringRequest);








        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    String username  = usernameEditText.getText().toString();


                    Intent mainIntent=new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.putExtra("username", username);

                    startActivity(mainIntent);
             //       finish();
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful

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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                loadingProgressBar.setVisibility(View.VISIBLE);
                for(int i=0;i<50;i++){


                    if(usernameEditText.getText().toString().equals(email[i])){
                        System.out.println("user found");

                        if(passwordEditText.getText().toString().equals(password[i])){
                            System.out.println("password matched");

                            flag=1;
                            index=i;
                        }
                    }
                }
                if(flag==1){
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                else{
                    System.out.println("NOOOOOTTT FOUNDDDD");
                    System.out.println(usernameEditText.getText().toString());
                    System.out.println(passwordEditText.getText().toString());
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();

                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Intent mainIntent=new Intent(LoginActivity.this, Register.class);
                startActivity(mainIntent);
                finish();

            }
        });
    }
    public String getUsername(){

        return username[index];
    }




    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = "hellloo  " +username[index]; //model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
