package com.erlantzoniga.stormcourse.core;

import dagger.Module;

/**
 * Mock Location calculator that returns "ES" if the latitude or longitude are negative or "US" is
 * positive.
 */
@Module
public class MockLocationCalculator implements ILocationCalculator {
  @Override
  public String getLocation(float latitude, float longitude) {
    if (latitude < 0f || longitude < 0f) {
      return  "ES";
    } else {
      return "US";
    }
  }
}
