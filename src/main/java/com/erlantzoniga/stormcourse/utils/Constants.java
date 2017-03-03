package com.erlantzoniga.stormcourse.utils;

public class Constants {
  public static final String TOPOLOGY_NAME = "StormCourseTopology";

  public static final String SPOUT_RANDOM_SENTENCE = "RANDOM_SENTENCE_SPOUT";

  public static final String BOLT_WORD_COUNT = "WORD_COUNT_BOLT";

  public static final String COUNT = "COUNT";
  public static final String WORD = "WORD";

  public static class Properties {
    public static final String RUN_ON_LOCAL = "storm.topology.local";
    public static final String LOCAL_TOPOLOGY_DURATION = "storm.topology.local.duration";
  }
}
