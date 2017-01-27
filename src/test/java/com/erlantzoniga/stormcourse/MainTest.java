package com.erlantzoniga.stormcourse;

import com.erlantzoniga.stormcourse.bolts.SplitSentenceBolt;
import com.erlantzoniga.stormcourse.bolts.WordCountBolt;
import com.erlantzoniga.stormcourse.spouts.RandomSentenceSpout;
import com.erlantzoniga.stormcourse.utils.Constants;

import java.net.URL;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Main.class, StormSubmitter.class})
public class MainTest {

  @Mock
  private Configurations mockConfigurations;
  @Mock
  private PropertiesConfiguration mockConfiguration;
  @Mock
  private TopologyBuilder mockBuilder;
  @Mock
  private StormTopology mockTopology;
  @Mock
  private Config mockStormConfig;
  @Mock
  private LocalCluster mockLocalCluster;

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void topologyShouldHaveAllBolts() throws Exception {
    // prepare
    PowerMockito.mockStatic(StormSubmitter.class);
    PowerMockito.whenNew(Configurations.class).withNoArguments().thenReturn(mockConfigurations);
    PowerMockito.whenNew(TopologyBuilder.class).withNoArguments().thenReturn(mockBuilder);

    doReturn(mockConfiguration).when(mockConfigurations).properties(any(URL.class));
    doReturn(mockTopology).when(mockBuilder).createTopology();
    doReturn(false).when(mockConfiguration).getBoolean(Constants.Configuration.RUN_ON_LOCAL, false);

    PowerMockito.doNothing()
        .when(StormSubmitter.class, "submitTopology", eq(Constants.TOPOLOGY_NAME),
            any(Config.class), eq(
                mockTopology));

    // act
    Main.main(new String[]{});

    // assert
    PowerMockito.verifyNew(Configurations.class).withNoArguments();
    PowerMockito.verifyNew(TopologyBuilder.class).withNoArguments();
    verify(mockBuilder)
        .setSpout(eq(Constants.SPOUT_RANDOM_SENTENCE), any(RandomSentenceSpout.class));
    verify(mockBuilder).setBolt(eq(Constants.BOLT_SPLIT_SENTENCE), any(SplitSentenceBolt.class));
    verify(mockBuilder).setBolt(eq(Constants.BOLT_WORD_COUNT), any(WordCountBolt.class));
    verify(mockConfiguration).getBoolean(Constants.Configuration.RUN_ON_LOCAL, false);
    verify(mockConfiguration).getInt(Constants.Configuration.TOPOLOGY_WORKERS, 1);
    PowerMockito.verifyStatic();
  }

  @Test
  public void topologyShouldRunOnLocal() throws Exception {
    // prepare
    //PowerMockito.spy(Thread.class);
    PowerMockito.whenNew(Configurations.class).withNoArguments().thenReturn(mockConfigurations);
    PowerMockito.whenNew(TopologyBuilder.class).withNoArguments().thenReturn(mockBuilder);
    PowerMockito.whenNew(Config.class).withNoArguments().thenReturn(mockStormConfig);
    PowerMockito.whenNew(LocalCluster.class).withNoArguments().thenReturn(mockLocalCluster);

    doReturn(mockConfiguration).when(mockConfigurations).properties(any(URL.class));
    doReturn(mockTopology).when(mockBuilder).createTopology();
    doReturn(true).when(mockConfiguration).getBoolean(Constants.Configuration.RUN_ON_LOCAL, false);
    //PowerMockito.doNothing().when(Thread.class, "sleep", 30000L);

    // act
    Main.main(new String[]{});

    // assert
    PowerMockito.verifyNew(Configurations.class).withNoArguments();
    PowerMockito.verifyNew(TopologyBuilder.class).withNoArguments();
    verify(mockStormConfig).setDebug(true);
    verify(mockLocalCluster).submitTopology(Constants.TOPOLOGY_NAME, mockStormConfig, mockTopology);
  }

}