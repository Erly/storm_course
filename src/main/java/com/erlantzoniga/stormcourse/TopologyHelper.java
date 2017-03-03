package com.erlantzoniga.stormcourse;

import com.erlantzoniga.stormcourse.spouts.RandomWordSpout;
import com.erlantzoniga.stormcourse.utils.Constants;
import com.erlantzoniga.stormcourse.utils.Sleeper;

import org.apache.commons.configuration2.Configuration;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

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
    topologyBuilder.setSpout(Constants.SPOUT_RANDOM_SENTENCE, new RandomWordSpout());
    // TODO: add the wordcount bolt;

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
      // TODO: Run topology in local mode

      sleeper.sleep(config.getInt(Constants.Properties.LOCAL_TOPOLOGY_DURATION, 10000));

      cluster.shutdown();
    } else {
      // TODO: Configure number of workers

      // TODO: Run topology in remote cluster
    }
  }
}
