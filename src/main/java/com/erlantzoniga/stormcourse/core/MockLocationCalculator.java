package com.erlantzoniga.stormcourse.core;

/**
 * Mock Location calculator that returns "ES" if the latitude or longitude are negative or "US" is
 * positive.
 */
public class MockLocationCalculator implements ILocationCalculator {
  @Override
  public String getLocation(float latitude, float longitude) {
    if (latitude < 0f || longitude < 0f) {
      return "ES";
    } else {
      return "US";
    }
  }
}
