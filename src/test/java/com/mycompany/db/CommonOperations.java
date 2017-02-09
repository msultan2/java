/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.db;

import static com.ninja_squad.dbsetup.Operations.*;
import com.ninja_squad.dbsetup.operation.Operation;

public final class CommonOperations {
  
  private CommonOperations() {}

  public static final Operation DELETE_ALL = deleteAllFrom("CUSTOMER", "DISCOUNT_CODE");
  
  // CHECKSTYLE IGNORE LineLength FOR NEXT 17 LINES
  public static final Operation INSERT_REFERENCE_DATA =
      sequenceOf(
      insertInto("CUSTOMER")
        .columns("CUSTOMER_ID", "DISCOUNT_CODE", "ZIP", "NAME", "ADDRESSLINE1", "ADDRESSLINE2", "CITY", "STATE", "PHONE", "FAX", "EMAIL", "CREDIT_LIMIT")
        .values(1, "N", "95117", "Jumbo Eagle Corp", "111 E. Las Olivas Blvd", "Suite 51", "Fort Lauderdale", "FL", "305-555-0188", "305-555-0189", "jumboeagle@example.com", 100000)
        .values(2, "M", "95035", "New Enterprises", "9754 Main Street", "P.O. Box 567", "Miami", "FL", "305-555-0148", "305-555-0149", "www.new.example.com", 50000)
        .values(25, "M", "85638", "Wren Computers", "8989 Red Albatross Drive", "Suite 9897", "Houston", "TX", "214-555-0133", "214-555-0134", "www.wrencomp.example.com", 25000)
        .values(3, "L", "12347", "Small Bill Company", "8585 South Upper Murray Drive", "P.O. Box 456", "Alanta", "GA", "555-555-0175", "555-555-0176", "www.smallbill.example.com", 90000)
        .values(36, "H", "94401", "Bob Hosting Corp.", "65653 Lake Road", "Suite 2323", "San Mateo", "CA", "650-555-0160", "650-555-0161", "www.bobhostcorp.example.com", 65000)
        .values(106, "L", "95035", "Early CentralComp", "829 E Flex Drive", "Suite 853", "San Jose", "CA", "408-555-0157", "408-555-0150", "www.centralcomp.example.com", 26500)
        .values(149, "L", "95117", "John Valley Computers", "4381 Kelly Valley Ave", "Suite 77", "Santa Clara", "CA", "408-555-0169", "408-555-0167", "www.johnvalley.example.com", 70000)
        .values(863, "N", "94401", "Big Network Systems", "456 444th Street", "Suite 45", "Redwood City", "CA", "650-555-0181", "650-555-0180", "www.bignet.example.com", 25000)
        .values(777, "L", "48128", "West Valley Inc.", "88 Northsouth Drive", "Building C", "Dearborn", "MI", "313-555-0172", "313-555-0171", "www.westv.example.com", 100000)
        .values(753, "H", "48128", "Zed Motor Co", "2267 NE Michigan Ave", "Building 21", "Dearborn", "MI", "313-555-0151", "313-555-0152", "www.parts@ford.example.com", 5000000)
        .values(722, "N", "48124", "Big Car Parts", "52963 Notouter Dr", "Suite 35", "Detroit", "MI", "313-555-0144", "313-555-0145", "www.bparts.example.com", 50000)
        .values(409, "L", "10095", "Old Media Productions", "4400 527th Street", "Suite 562", "New York", "NY", "212-555-0110", "212-555-0111", "www.oldmedia.example.com", 10000)
        .values(410, "M", "10096", "Yankee Computer Repair Ltd", "9653 211th Ave", "Floor 4", "New York", "NY", "212-555-0191", "212-555-0197", "www.nycompltd@repair.example.com", 25000)
        .build(),
      insertInto("DISCOUNT_CODE")
        .columns("DISCOUNT_CODE", "RATE")
        .values("H", 16)
        .values("M", 11)
        .values("L", 7)
        .values("N", 0)
        .build());

}
