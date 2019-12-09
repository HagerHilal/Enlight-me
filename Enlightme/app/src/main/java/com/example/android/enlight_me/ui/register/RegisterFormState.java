package com.example.android.enlight_me.ui.register;

import androidx.annotation.Nullable;

 public class RegisterFormState {


        @Nullable
        private Integer usernameError;
        @Nullable
        private Integer passwordError;
        @Nullable
        private Integer emailError;

        private boolean isDataValid;

        RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,@Nullable Integer emailError) {
            this.usernameError = usernameError;
            this.passwordError = passwordError;
            this.emailError=emailError;
            this.isDataValid = false;
        }

        RegisterFormState(boolean isDataValid) {
            this.usernameError = null;
            this.passwordError = null;
            this.emailError=null;
            this.isDataValid = isDataValid;
        }

        @Nullable
        public Integer getUsernameError() {
            return usernameError;
        }

        @Nullable
        public Integer getPasswordError() {
            return passwordError;
        }

     @Nullable
     public Integer getEmailError() {
         return emailError;
     }

        public boolean isDataValid() {
            return isDataValid;
        }
    }


