package com.erlantzoniga.storm_course;

import com.erlantzoniga.storm_course.spouts.RandomSentenceSpout;
import com.erlantzoniga.storm_course.utils.Constants;
import com.erlantzoniga.storm_course.utils.Sleeper;

import org.apache.commons.configuration2.Configuration;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class TopologyHelper {

  private TopologyBuilder topologyBuilder;
  private Configuration config;
  private Sleeper sleeper = new Sleeper();
  private Config conf = new Config();

  TopologyHelper(){}

  public TopologyHelper(TopologyBuilder topologyBuilder, Configuration config) {
    this.topologyBuilder = topologyBuilder;
    this.config = config;
  }

  public TopologyHelper configure() {
    topologyBuilder.setSpout(Constants.SPOUT_RANDOM_SENTENCE, new RandomSentenceSpout());
    // TODO: add the wordcount bolt;

    return this;
  }

  public void run() throws InterruptedException {
    if (config.getBoolean(Constants.Properties.RUN_ON_LOCAL, false)) {
      conf.setDebug(true);

      LocalCluster cluster = new LocalCluster();
      // TODO: Run topology in local mode

      sleeper.sleep(config.getInt(Constants.Properties.LOCAL_TOPOLOGY_DURATION, 10000));

      cluster.shutdown();
    } else {
      // TODO: Configure number of workers

      // TODO: Run topology in remote cluster
    }
  }
}
