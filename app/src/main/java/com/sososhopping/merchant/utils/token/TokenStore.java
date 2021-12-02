package com.sososhopping.merchant.utils.token;

public class TokenStore {

    static String authToken;
    static String firebaseToken;

    public static String getAuthToken() {
        return authToken;
    }

    public static void storeAuthToken(String authToken) {
        TokenStore.authToken = authToken;
    }

    public static String getFirebaseToken() {
        return firebaseToken;
    }

    public static void storeFirebaseToken(String firebaseToken) {
        TokenStore.firebaseToken = firebaseToken;
    }
}
