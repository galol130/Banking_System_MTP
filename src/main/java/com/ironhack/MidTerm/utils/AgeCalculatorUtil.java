package com.ironhack.MidTerm.utils;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculatorUtil {

    public static Period calculateAgeFromBirthday(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now());
    }

    public static boolean isOlderThan(LocalDate birthday, Integer years) {
        Period diff = Period.between(birthday, LocalDate.now());
        return diff.getYears() >= years;
    }
}
