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
        private String nimToken;
        private boolean login;
        private String wxAppId;
        private boolean firstSplash;
        private boolean firstLanguage;


        private String cardNo;
        private String userId;
        private String userHeader;

        private final String KEY_PHONE = "phone";
        private final String KEY_CARDNO = "cardNo";
        private final String KEY_PASSWORD = "password";
        private final String KEY_TOKEN = "token";
        private final String KEY_NIM_TOKEN = "nim_token";
        private final String KEY_LOGIN = "login";
        private final String KEY_WX_APPID = "wx_app_id";
        private final String KEY_SPLASH = "splash";
        private final String KEY_USERID = "userId";
        private final String KEY_USER_HEADER = "userHeader";
        private final String KEY_FIRST_LANGUAGE = "language";

        public void clear() {
            savePassword(null);
            savePhone(null);
            saveToken(null);
            setCardNo(null);
            setUserId(null);
            setUserHeader(null);
            setLogin(false);
        }

        public String getCardNo() {
            if (cardNo == null)
                cardNo = getString(KEY_CARDNO);
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            saveString(KEY_CARDNO, cardNo);
            this.cardNo = cardNo;
        }


        public String getUserHeader() {
            if (userHeader == null)
                userHeader = getString(KEY_USER_HEADER);
            return userHeader;
        }

        public void setUserHeader(String userHeader) {
            saveString(KEY_USER_HEADER, userHeader);
            this.userHeader = userHeader;
        }
        public String getUserId() {
            if (userId == null)
                userId = getString(KEY_USERID);
            return userId;
        }

        public void setUserId(String userId) {
            saveString(KEY_USERID, userId);
            this.userId = userId;
        }
        public String getPhone() {
            if (phone == null)
                phone = getString(KEY_PHONE);
            return phone;
        }

        public void savePhone(String phone) {
            this.phone = phone;
            saveString(KEY_PHONE, phone);
        }

        public String getWxAppId() {
            if (wxAppId == null)
                wxAppId = getString(KEY_WX_APPID);
            return wxAppId;
        }

        public void saveWxAppId(String wxAppId) {
            this.wxAppId = wxAppId;
            saveString(KEY_WX_APPID, wxAppId);
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

        public boolean isLogin() {
            return getBoolean(KEY_LOGIN);
        }

        public void setLogin(boolean login) {
            this.login = login;
            saveBoolean(KEY_LOGIN, login);
        }

        public String getNimToken() {
            if (nimToken == null) {
                nimToken = getString(KEY_NIM_TOKEN);
            }
            return nimToken;
        }

        public void saveNimToken(String nimToken) {
            this.nimToken = nimToken;
            saveString(KEY_NIM_TOKEN, nimToken);
        }

        public void setFirstSplash(boolean firstSplash) {
            this.firstSplash = firstSplash;
            saveBoolean(KEY_SPLASH, firstSplash);
        }

        public boolean isFirstSplash() {
            return getBoolean(KEY_SPLASH);
        }

        public void setFirstLanguage(boolean firstLanguage) {
            this.firstLanguage = firstLanguage;
            saveBoolean(KEY_FIRST_LANGUAGE, firstLanguage);
        }

        public boolean isFirstLanguage() {
            return getBoolean(KEY_FIRST_LANGUAGE);
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

    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    static SharedPreferences getSharedPreferences() {
        return AppApplication.getInstance().getSharedPreferences("Account", Context.MODE_PRIVATE);
    }
}
