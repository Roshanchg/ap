package com.example.ap;

import java.util.Locale;

public class LocaleStorageSingleton {
    public static Locale locale=Locale.of("en");
    public static Locale getLocale(){
        return locale;
    }
    public static void setLocaleNp(){
        locale=Locale.of("np");
    }
    public static void setLocaleEn(){
        locale=Locale.of("en");
    }
}
