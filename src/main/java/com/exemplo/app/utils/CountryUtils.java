package com.exemplo.app.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryUtils {

    private static final Map<String, String> NAME_TO_ISO = new HashMap<>();

    static {
        for (String iso : Locale.getISOCountries()){
            Locale l = new Locale("", iso);
            NAME_TO_ISO.put(l.getDisplayCountry(Locale.ENGLISH).toUpperCase(), iso);
        }

        NAME_TO_ISO.put("SOUTH KOREA", "KR");
        NAME_TO_ISO.put("NORTH KOREA", "KP");
        NAME_TO_ISO.put("RUSSIA", "RU");
        NAME_TO_ISO.put("VIETNAM", "VN");
        NAME_TO_ISO.put("LAOS", "LA");
        NAME_TO_ISO.put("IRAN", "IR");
        NAME_TO_ISO.put("SYRIA", "SY");
    }

    public static String getIsoCode(String nomePais){
        if (nomePais == null) return null;

        return NAME_TO_ISO.get(nomePais.toUpperCase().trim());
    }
}
