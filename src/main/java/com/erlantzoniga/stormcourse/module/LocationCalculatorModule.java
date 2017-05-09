package com.erlantzoniga.stormcourse.module;

import com.erlantzoniga.stormcourse.core.ILocationCalculator;
import com.erlantzoniga.stormcourse.core.MockLocationCalculator;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationCalculatorModule {
  @Provides
  static ILocationCalculator provideLocationCalculator() {
    return new MockLocationCalculator();
  }
}
