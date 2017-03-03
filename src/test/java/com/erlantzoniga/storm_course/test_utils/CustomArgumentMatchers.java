package com.erlantzoniga.storm_course.test_utils;

import static org.mockito.ArgumentMatchers.argThat;

import org.apache.storm.tuple.Fields;

import org.mockito.ArgumentMatcher;

public class CustomArgumentMatchers {

  /**
   * Custom matcher for verifying actual and expected Fields match.
   */
  public static class FieldsMatcher implements ArgumentMatcher<Fields> {

    private final Fields expected;

    public FieldsMatcher(Fields expected) {
      this.expected = expected;
    }

    @Override
    public boolean matches(Fields actual) {
      return actual.toList().equals(expected.toList());
    }
  }

  public static Fields fieldsEq(Fields expected) {
    return argThat(new FieldsMatcher(expected));
  }
}
