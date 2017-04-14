/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.date_time;

import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

/**
 * The Java Tutorial. A Short Course on the Basics. Sixth Edition. Chapter 21: Date-Time
 */
public class FamilyHolidays implements TemporalQuery<Boolean> {

  /**
   * Returns true if the passed-in date occurs during one of the family vacations. Because the query
   * compares the month and day only, the check succeeds even if the Temporal types are not the
   * same.
   */
  @Override
  public Boolean queryFrom(final TemporalAccessor temporal) {
    int month = temporal.get(ChronoField.MONTH_OF_YEAR);
    int day = temporal.get(ChronoField.DAY_OF_MONTH);

    // Disneyland over Spring Break
    if (day >= 3 && day <= 8 && month == APRIL.getValue()) {
      return Boolean.TRUE;
    }

    // Smith family reunion on Lake Saugatuck
    if (day >= 8 && day <= 14 && month == AUGUST.getValue()) {
      return Boolean.TRUE;
    }

    return Boolean.FALSE;
  }

}
