package com.examen.api.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Utils {

  public static Boolean validateIsOfLegalAge(Date birthDate) {
    LocalDate birthLocalDate = new java.sql.Date(birthDate.getTime()).toLocalDate();
    LocalDate today = LocalDate.now();
    int age = Period.between(birthLocalDate, today).getYears();

    return age >= 18;
  }

  public static Timestamp getDateNow() {
    long currentTimeMillis = System.currentTimeMillis();

    return new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));
  }

  public static Timestamp setTimestamp(Date date, String hours) {
    String dateString = date.toString() + "T" + hours+".000Z";
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
    Timestamp timestamp = Timestamp.from(zonedDateTime.toInstant());

    return timestamp;
  }
}
