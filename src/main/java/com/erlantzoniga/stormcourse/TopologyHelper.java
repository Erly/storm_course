package com.erlantzoniga.stormcourse;

import com.erlantzoniga.stormcourse.bolts.WordCountBolt;
import com.erlantzoniga.stormcourse.spouts.RandomWordSpout;
import com.erlantzoniga.stormcourse.utils.Constants;
import com.erlantzoniga.stormcourse.utils.Sleeper;

import org.apache.commons.configuration2.Configuration;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class TopologyHelper {

  private TopologyBuilder topologyBuilder;
  private Configuration config;
  private Sleeper sleeper = new Sleeper();
  private Config conf = new Config();

  TopologyHelper() {
  }

  public TopologyHelper(TopologyBuilder topologyBuilder, Configuration config) {
    this.topologyBuilder = topologyBuilder;
    this.config = config;
  }

  /**
   * Configures the spouts and bolts of the topology.
   *
   * @return The same instance so you can call it like this topologyHelper.configure().run();
   */
  public TopologyHelper configure() {
    // TODO: Change the RandomWordSpout for the RandomTweetSpout
    topologyBuilder.setSpout(Constants.SPOUT_RANDOM_WORD, new RandomWordSpout());
    // TODO: Add the SentenceSplitterBolt (use localOrShuffleGrouping)
    // TODO: Modify the topology so the WordCountBolt reads the SentenceSplitterBolt stream
    topologyBuilder.setBolt(Constants.BOLT_WORD_COUNT, new WordCountBolt())
        .fieldsGrouping(Constants.SPOUT_RANDOM_WORD, new Fields(Constants.WORD));

    return this;
  }

  /**
   * Runs the topology. It will be run in local if the property storm.topology.local is true or
   * on remote otherwise (false or non-existent)
   *
   * @throws InterruptedException     if the Thread.sleep used when running on local is interrupted
   * @throws AlreadyAliveException    if a topology with this name is already running in the remote
   *                                  cluster
   * @throws InvalidTopologyException if an invalid topology is submitted
   * @throws AuthorizationException   if authorization is failed
   */
  public void run() throws InterruptedException, AlreadyAliveException, InvalidTopologyException,
      AuthorizationException {
    if (config.getBoolean(Constants.Properties.RUN_ON_LOCAL, false)) {
      conf.setDebug(true);

      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology(Constants.TOPOLOGY_NAME, conf, topologyBuilder.createTopology());

      sleeper.sleep(config.getInt(Constants.Properties.LOCAL_TOPOLOGY_DURATION, 10000));

      cluster.shutdown();
    } else {
      conf.setNumWorkers(config.getInt(Config.TOPOLOGY_WORKERS, 1));

      StormSubmitter
          .submitTopology(Constants.TOPOLOGY_NAME, conf, topologyBuilder.createTopology());
    }
  }
}
