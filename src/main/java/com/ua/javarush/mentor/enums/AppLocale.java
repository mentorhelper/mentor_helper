package com.ua.javarush.mentor.enums;

import java.util.Locale;

public enum AppLocale {
    EN("EN", Locale.ENGLISH),
    RU("RU", new Locale("ru", "RU")),
    UA("UA", new Locale("ua", "UA"));

    private final String locale;
    private final Locale localeObject;

    AppLocale(String locale, Locale localeObject) {
        this.locale = locale;
        this.localeObject = localeObject;
    }

    public Locale getLocaleObject() {
        return localeObject;
    }
}
