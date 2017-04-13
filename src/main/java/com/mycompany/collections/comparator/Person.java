/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.collections.comparator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressFBWarnings(value = "USBR_UNNECESSARY_STORE_BEFORE_RETURN",
        justification = "Person.hashCode() is auto-generated")
@Data
@AllArgsConstructor
public class Person {

  private final String name;
  private final int age;

  public int ageDifference(final Person other) {
    return age - other.age;
  }

}
