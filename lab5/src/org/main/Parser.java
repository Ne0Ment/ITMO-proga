package org.main;

import org.main.data.OrganizationType;
import org.main.data.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Responsible for parsing and formatting.
 * @author neoment
 * @version 0.1
 */
public class Parser {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy H:m:s", Locale.ENGLISH);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:m:s", Locale.ENGLISH);
    public static Object parseString(Class<?> cl, String s) throws ParseException, DateTimeParseException, IllegalArgumentException {
        s = s.trim();
        if (Objects.equals(s, "") || Objects.equals(s, "null")) return null;
        try {
            if (Integer.class == cl || Integer.TYPE == cl ) return Integer.parseInt( s );
            if (Long.class == cl || Long.TYPE == cl) return Long.parseLong( s );
            if (Float.class == cl || Float.TYPE == cl) return Float.parseFloat( s );
            if (Double.class == cl || Double.TYPE == cl) return Double.parseDouble( s );
            if (Date.class == cl) return dateFormatter.parse(s);
            if (LocalDateTime.class == cl) return LocalDateTime.parse(s, dateTimeFormatter);
            if (Position.class == cl) return Position.valueOf(s);
            if (OrganizationType.class == cl) return OrganizationType.valueOf(s);
        } catch (ParseException | DateTimeParseException | NumberFormatException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong input value, try again.");
        }
        return s;
    }

    public static String genString(Object obj) {
        if (obj==null) return "null";
        if (obj.getClass() == Date.class) return dateFormatter.format(obj);
        if (obj.getClass() == LocalDateTime.class) return ((LocalDateTime) obj).format(dateTimeFormatter);
        return obj.toString();
    }

    public static String formatExample(Class <?> cl) {
        if (String.class == cl) return "amogus";
        if (Integer.class == cl || Integer.TYPE == cl ) return "1";
        if (Long.class == cl || Long.TYPE == cl) return "1";
        if (Float.class == cl || Float.TYPE == cl) return "1.2";
        if (Double.class == cl || Double.TYPE == cl) return "1.2";
        if (Date.class == cl) return "26-05-1976 18:35:00";
        if (LocalDateTime.class == cl) return "26-05-1976 18:35:00";
        if (cl.isEnum()) return cl.getEnumConstants()[0].toString();
        return "undefined";
    }
}
