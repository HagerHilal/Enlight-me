package com.example.android.enlight_me.ui.register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.enlight_me.R;
import com.example.android.enlight_me.data.LoginRepository;
import com.example.android.enlight_me.data.Result;
import com.example.android.enlight_me.ui.register.RegisterFormState;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {



        private static MutableLiveData<RegisterFormState> RegisterFormState = new MutableLiveData<>();
     //   private RegisterRepository RegisterRepository;


      public static LiveData<RegisterFormState> getRegisterFormState() {
            return RegisterFormState;
        }




        public static void RegisterDataChanged(String username, String password, String email) {
            if (!isUserNameValid(username)) {
                RegisterFormState.setValue(new RegisterFormState(R.string.invalid_username, null,null));
            } else if (!isPasswordValid(password)) {
                RegisterFormState.setValue(new RegisterFormState(null, R.string.invalid_password,null));
            }else if (!isEmailValid(email)) {
                RegisterFormState.setValue(new RegisterFormState(null, null,R.string.email));
            }
            else {
                RegisterFormState.setValue(new RegisterFormState(true));
            }
        }

        // A placeholder username validation check
        private static boolean isEmailValid(String email) {
            if (email == null) {
                return false;
            }
            if (email.contains("@")) {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            } else {
                return false;//!username.trim().isEmpty();
            }
        }

        // A placeholder password validation check
        private static boolean isPasswordValid(String password) {
            Pattern pattern;
            Matcher matcher;

            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&+=])(?=\\S+$).{4,}$";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return password != null && password.trim().length() > 7 && matcher.matches();
        }

    private static boolean isUserNameValid(String username) {
        return username != null && username.trim().length() > 5;
    }

    }


