package com.ua.javarush.mentor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Countries {
    UKRAINE("Ukraine"),
    RUSSIA("Russia"),
    USA("USA"),
    BELARUS("Belarus"),
    GERMANY("Germany"),
    FRANCE("France"),
    ITALY("Italy"),
    SPAIN("Spain"),
    POLAND("Poland"),
    CHINA("China"),
    JAPAN("Japan"),
    INDIA("India"),
    CANADA("Canada"),
    BRAZIL("Brazil"),
    MEXICO("Mexico"),
    AUSTRALIA("Australia"),
    ARGENTINA("Argentina"),
    KAZAKHSTAN("Kazakhstan"),
    TURKEY("Turkey"),
    UK("UK"),
    SWEDEN("Sweden"),
    NORWAY("Norway"),
    DENMARK("Denmark"),
    FINLAND("Finland"),
    NETHERLANDS("Netherlands"),
    SWITZERLAND("Switzerland"),
    AUSTRIA("Austria"),
    PORTUGAL("Portugal"),
    GREECE("Greece"),
    IRELAND("Ireland"),
    NEW_ZEALAND("New Zealand"),
    SINGAPORE("Singapore"),
    HUNGARY("Hungary"),
    CZECH_REPUBLIC("Czech Republic"),
    ROMANIA("Romania"),
    BULGARIA("Bulgaria"),
    BELGIUM("Belgium"),
    SLOVAKIA("Slovakia"),
    SLOVENIA("Slovenia"),
    LITHUANIA("Lithuania"),
    LATVIA("Latvia"),
    ESTONIA("Estonia"),
    CROATIA("Croatia"),
    SERBIA("Serbia"),
    BOSNIA_AND_HERZEGOVINA("Bosnia and Herzegovina"),
    MONTENEGRO("Montenegro"),
    MACEDONIA("Macedonia"),
    ALBANIA("Albania"),
    MALTA("Malta"),
    CYPRUS("Cyprus"),
    LUXEMBOURG("Luxembourg"),
    MALAYSIA("Malaysia"),
    CHILE("Chile"),
    COLOMBIA("Colombia"),
    PERU("Peru"),
    VENEZUELA("Venezuela"),
    ECUADOR("Ecuador"),
    URUGUAY("Uruguay"),
    PARAGUAY("Paraguay");

    private final String country;

    public static boolean contains(String country) {
        for (Countries c : Countries.values()) {
            if (c.getCountry().equals(country)) {
                return true;
            }
        }
        return false;
    }
}
