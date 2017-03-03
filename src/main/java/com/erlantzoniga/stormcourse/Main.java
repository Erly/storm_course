package com.erlantzoniga.stormcourse;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.storm.topology.TopologyBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Configurations configs = new Configurations();
    try {
      Configuration config = configs.properties(
          Thread.currentThread().getContextClassLoader().getResource("storm.properties"));

      TopologyBuilder builder = new TopologyBuilder();

      new TopologyHelper(builder, config).configure().run();
    } catch (Exception ex) {
      LOG.error("An exception occurred in main: {}", ex.getMessage(), ex);
    }
  }
}
