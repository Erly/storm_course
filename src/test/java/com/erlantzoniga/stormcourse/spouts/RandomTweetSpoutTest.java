package com.erlantzoniga.stormcourse.spouts;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Values;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class RandomTweetSpoutTest {

  private RandomTweetSpout randomTweetSpout;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setup() {
    randomTweetSpout = new RandomTweetSpout();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void cleanup() {
    System.setOut(null);
  }

  @Test
  public void ack_ok() {
    // prepare
    UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
    randomTweetSpout.emittedTuples.put(uuid, "Test sentence");

    // act
    randomTweetSpout.ack(uuid);

    // assert
    assertThat(outContent.toString()).isEqualTo("123e4567-e89b-12d3-a456-426655440000 acked");
    assertThat(randomTweetSpout.emittedTuples).hasSize(0);
  }

  @Test
  public void fail_ok() {
    // prepare
    UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
    SpoutOutputCollector mockCollector = Mockito.mock(SpoutOutputCollector.class);
    randomTweetSpout.open(Mockito.mock(Map.class), Mockito.mock(TopologyContext.class), mockCollector);
    randomTweetSpout.emittedTuples.put(uuid, "Test sentence");

    // act
    randomTweetSpout.fail(uuid);

    // assert
    assertThat(outContent.toString()).isEqualTo("123e4567-e89b-12d3-a456-426655440000 failed");
    Mockito.verify(mockCollector).emit(new Values("Test sentence"), uuid);
  }
}
