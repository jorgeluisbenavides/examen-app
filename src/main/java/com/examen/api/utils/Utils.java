package com.examen.api.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Utils {

  public static Boolean validateIsOfLegalAge(Date birthDate) {
    LocalDate birthLocalDate = new java.sql.Date(birthDate.getTime()).toLocalDate();
    LocalDate today = LocalDate.now();
    int age = Period.between(birthLocalDate, today).getYears();

    return age >= 18;
  }
}
