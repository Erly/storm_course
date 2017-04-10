package com.erlantzoniga.stormcourse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.erlantzoniga.stormcourse.bolts.SentenceSplitterBolt;
import com.erlantzoniga.stormcourse.bolts.WordCountBolt;
import com.erlantzoniga.stormcourse.spouts.RandomTweetSpout;
import com.erlantzoniga.stormcourse.spouts.RandomWordSpout;
import com.erlantzoniga.stormcourse.testutils.CustomArgumentMatchers;
import com.erlantzoniga.stormcourse.utils.Constants;
import com.erlantzoniga.stormcourse.utils.Sleeper;

import org.apache.commons.configuration2.Configuration;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.BoltDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PowerMockIgnore("javax.management.*")
@PrepareForTest({TopologyHelper.class, StormSubmitter.class})
@RunWith(PowerMockRunner.class)
public class TopologyHelperTest {

  @InjectMocks
  TopologyHelper topologyHelper;

  @Mock
  TopologyBuilder mockTopologyBuilder;
  @Mock
  Configuration mockConfig;
  @Mock
  Sleeper mockSleeper;
  @Mock
  Config mockConf;
  @Mock
  StormTopology mockTopology;

  @Before
  public void setUp() {
    // necessary to reinject the mocks of the Sleeper and Config classes
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void test_configure() {
    // prepare
    BoltDeclarer mockSentenceSplitterBoltDeclarer = mock(BoltDeclarer.class);
    BoltDeclarer mockWordCountBoltDeclarer = mock(BoltDeclarer.class);
    doReturn(mockSentenceSplitterBoltDeclarer).when(mockTopologyBuilder).setBolt(eq(Constants.BOLT_SENTENCE_SPLITTER), any(SentenceSplitterBolt.class));
    doReturn(mockWordCountBoltDeclarer).when(mockTopologyBuilder).setBolt(eq(Constants.BOLT_WORD_COUNT), any(WordCountBolt.class));

    // act
    TopologyHelper returnedTopologyHelper = topologyHelper.configure();

    // assert
    verify(mockTopologyBuilder).setSpout(eq(Constants.SPOUT_RANDOM_TWEET), any(RandomTweetSpout.class));
    verify(mockTopologyBuilder).setBolt(eq(Constants.BOLT_SENTENCE_SPLITTER), any(
        SentenceSplitterBolt.class));
    verify(mockTopologyBuilder).setBolt(eq(Constants.BOLT_WORD_COUNT), any(WordCountBolt.class));
    verify(mockSentenceSplitterBoltDeclarer).localOrShuffleGrouping(eq(Constants.SPOUT_RANDOM_TWEET));
    verify(mockWordCountBoltDeclarer).fieldsGrouping(eq(Constants.BOLT_SENTENCE_SPLITTER), CustomArgumentMatchers.fieldsEq(new Fields(Constants.WORD)));
    assertThat(returnedTopologyHelper).isEqualTo(topologyHelper);
  }

  @Test
  public void test_run_on_local() throws Exception {
    // prepare
    LocalCluster mockLocalCluster = mock(LocalCluster.class); // this mock takes a lot of time
    doReturn(true).when(mockConfig).getBoolean(Constants.Properties.RUN_ON_LOCAL, false);
    doReturn(mockTopology).when(mockTopologyBuilder).createTopology();
    doReturn(1).when(mockConfig).getInt(Constants.Properties.LOCAL_TOPOLOGY_DURATION, 10000);
    PowerMockito.whenNew(LocalCluster.class).withNoArguments().thenReturn(mockLocalCluster);

    // act
    topologyHelper.run();

    // assert
    verify(mockConf).setDebug(true);
    verify(mockLocalCluster).submitTopology(Constants.TOPOLOGY_NAME, mockConf, mockTopology);
    verify(mockSleeper).sleep(1);
    verify(mockLocalCluster).shutdown();
  }

  @Test
  public void test_run_on_remote() throws Exception {
    // prepare
    doReturn(false).when(mockConfig).getBoolean(Constants.Properties.RUN_ON_LOCAL, false);
    doReturn(mockTopology).when(mockTopologyBuilder).createTopology();
    doReturn(2).when(mockConfig).getInt(Config.TOPOLOGY_WORKERS, 1);
    PowerMockito.mockStatic(StormSubmitter.class);

    // act
    topologyHelper.run();

    // assert
    verify(mockConf).setNumWorkers(2);
    PowerMockito.verifyStatic();
    StormSubmitter.submitTopology(Constants.TOPOLOGY_NAME, mockConf, mockTopology);
  }
}
