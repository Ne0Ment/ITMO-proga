package org.neoment.client;

public class ClientAccountHandler {
    public static String username = null;
    public static String password = null;

    public static void setAccount(String username, String password) {
        ClientAccountHandler.username = username;
        ClientAccountHandler.password = password;
    }

    public static void clearAccount() {
        username = null;
        password = null;
    }

    public static boolean isLoggedIn(){
        return username!=null && password!=null;
    }
}
