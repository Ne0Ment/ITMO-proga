package org.neoment.shared;

public class AccountVerifier {
    private static int maxLen = 127;
    public static boolean checkPassInput(String pass) {
        return pass.length()<=maxLen && pass.length()>0;
    }

    public static boolean checkLoginInput(String login) {
        return login.length()<=maxLen && login.length()>0;
    }
}
