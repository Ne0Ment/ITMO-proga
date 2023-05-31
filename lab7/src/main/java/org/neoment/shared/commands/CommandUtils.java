package org.neoment.shared.commands;

public class CommandUtils {
    public static Object[][] wrap(Object... o) {
        return new Object[][] { o };
    }

    public static Object[][] wrap(Object[][] o) {
        return o;
    }
}
