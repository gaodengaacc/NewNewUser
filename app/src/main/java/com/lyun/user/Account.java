package com.lyun.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ZHAOWEIWEI on 2017/2/15.
 */

public class Account {

    public static Preference preference() {
        return Preference.instance();
    }

    public static class Preference {

        public static Preference mInstance;

        public static Preference instance() {
            if (mInstance == null) {
                mInstance = new Preference();
            }
            return mInstance;
        }

        private String phone;
        private String password;
        private String token;

        private final String KEY_PHONE = "phone";
        private final String KEY_PASSWORD = "password";
        private final String KEY_TOKEN = "token";

        public String getPhone() {
            if (phone == null)
                phone = getString(KEY_PHONE);
            return phone;
        }

        public void savePhone(String phone) {
            this.phone = phone;
            saveString(KEY_PHONE, phone);
        }

        public String getPassword() {
            if (password == null)
                password = getString(KEY_PASSWORD);
            return password;
        }

        public void savePassword(String password) {
            this.password = password;
            saveString(KEY_PASSWORD, password);
        }

        public String getToken() {
            if (token == null)
                token = getString(KEY_TOKEN);
            return token;
        }

        public void saveToken(String token) {
            this.token = token;
            saveString(KEY_TOKEN, token);
        }
    }


    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        return AppApplication.getInstance().getSharedPreferences("Account", Context.MODE_PRIVATE);
    }
}
