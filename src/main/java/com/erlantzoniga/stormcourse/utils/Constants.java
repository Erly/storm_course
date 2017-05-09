package com.erlantzoniga.stormcourse.utils;

public class Constants {
  public static final String TOPOLOGY_NAME = "StormCourseTopology";

  public static final String SPOUT_RANDOM_WORD = "RANDOM_WORD_SPOUT";
  public static final String SPOUT_RANDOM_TWEET = "RANDOM_TWEET_SPOUT";

  public static final String BOLT_WORD_COUNT = "WORD_COUNT_BOLT";
  public static final String BOLT_SENTENCE_SPLITTER = "SENTENCE_SPLITTER_BOLT";
  public static final String BOLT_LOCATION_CALCULATOR = "LOCATION_CALCULATOR_BOLT";

  public static final String STREAM_WORD = "WORD_STREAM";
  public static final String STREAM_COORDINATES = "COORDINATES_STREAM";

  public static final String COUNT = "COUNT";
  public static final String WORD = "WORD";
  public static final String ID = "ID";
  public static final String TWEET = "TWEET";
  public static final String LATITUDE = "LATITUDE";
  public static final String LONGITUDE = "LONGITUDE";
  public static final String COUNTRY_CODE = "COUNTRY_CODE";

  public static class Properties {
    public static final String RUN_ON_LOCAL = "storm.topology.local";
    public static final String LOCAL_TOPOLOGY_DURATION = "storm.topology.local.duration";
  }
}
